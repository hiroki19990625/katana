package jp.katana.client

import io.netty.util.ResourceLeakDetector
import jp.katana.client.command.ClientCommandSender
import jp.katana.client.console.KatanaConsole
import jp.katana.client.event.EventManager
import jp.katana.client.event.client.ClientStartEvent
import jp.katana.client.event.client.ClientStopEvent
import jp.katana.client.event.client.ClientUpdateTickEvent
import jp.katana.client.factory.FactoryManager
import jp.katana.core.ClientState
import jp.katana.core.IClient
import jp.katana.core.IClientProperties
import jp.katana.core.ServerState
import jp.katana.core.command.ICommandSender
import jp.katana.core.event.IEventManager
import jp.katana.core.factory.IFactoryManager
import jp.katana.i18n.I18n
import jp.katana.server.factory.CommandFactory
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LoggerContext
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.net.InetAddress

class Client : IClient {
    companion object {
        init {
            System.setProperty("log4j.skipJansi", "false")
            System.setProperty("log4j.configurationFile", "log4j2-katana.xml")

            // TODO: Memory Leak Ignore
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED)

            Thread.currentThread().name = I18n["katana.server.thread.hostThread"]
        }

        const val PROTOCOL_VERSION = 407
        const val GAME_VERSION = "1.16.0"
        const val INTERNAL_GAME_VERSION = 17694723
    }

    override val protocolVersion: Int = PROTOCOL_VERSION
    override val gameVersion: String = GAME_VERSION

    override var propertiesFile: File = File("client-properties.yml")
    override var clientProperties: IClientProperties? = null
        private set
    override var state: ClientState = ClientState.Stopped
        private set
    override val logger = LogManager.getLogger(Client::class.java)!!
    override val console = KatanaConsole(this)

    override var factoryManager: IFactoryManager = FactoryManager(this)
    override var eventManager: IEventManager = EventManager()

    override val serverPort: Int
        get() = clientProperties!!.serverPort
    override val serverAddress: InetAddress
        get() = InetAddress.getByName(clientProperties!!.serverIp)

    override val tickRate: Byte
        get() = clientProperties!!.tickRate
    override var totalTick: Long = 0
        private set

    private val consoleThread = Thread { startConsole() }
    private val mainThread = Thread { startMainThread() }

    private val clientCommandSender = ClientCommandSender(this)

    override fun start() {
        state = ClientState.Running
        consoleThread.name = I18n["katana.client.thread.consoleThread"]
        consoleThread.start()

        logger.info(I18n["katana.client.starting"])

        try {
            loadClientProperties()

            updateLogger()

            logger.info(I18n["katana.client.network.starting"])
            // networkManager = networkManagerCreator(this)
            if (state == ServerState.Stopped)
                return
            // networkManager?.start()
            logger.info(I18n["katana.client.network.startInfo", serverPort])
        } catch (e: Exception) {
            logger.error("", e)
            shutdownForce()
            return
        }

        val event = ClientStartEvent(this)
        eventManager(event, ClientStartEvent::class.java)

        mainThread.start()
    }

    override fun shutdown(): Boolean {
        state = ClientState.Stopping
        logger.info(I18n["katana.client.stopping"])

        val event = ClientStopEvent(this)
        eventManager(event, ClientStopEvent::class.java)

        try {
            saveClientProperties()

            // networkManager?.shutdown()
        } catch (e: Exception) {
            logger.error("", e)
            return shutdownForce()
        }

        if (consoleThread.isAlive)
            consoleThread.interrupt()

        state = ClientState.Stopped
        logger.info(I18n["katana.client.stop"])
        return true
    }

    override fun shutdownForce(): Boolean {
        state = ClientState.Stopped
        logger.info(I18n["katana.client.stop"])

        return true
    }

    override fun update(tick: Long): Boolean {
        return try {
            val command = console.readCommand()
            if (command != null) {
                executeCommand(clientCommandSender, command)
            }
            true
        } catch (e: Exception) {
            logger.warn("", e)
            false
        }
    }

    override fun executeCommand(sender: ICommandSender, command: String) {
        try {
            val split = command.split(' ')
            val label = split[0]
            val args = split.drop(1)
            val cmd = factoryManager.get(CommandFactory::class.java)!![command]
            if (cmd != null)
                cmd.execute(sender, label, args)
            else
                logger.info(I18n["katana.server.command.generic.unknown", label])
        } catch (e: Exception) {
            logger.warn("", e)
        }
    }

    private fun startConsole() {
        try {
            console.start()
        } catch (e: Exception) {
            logger.error("", e)
            shutdownForce()
        }
    }

    private fun loadClientProperties() {
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val yaml = Yaml(options)
        if (propertiesFile.isFile && propertiesFile.exists()) {
            val reader = propertiesFile.reader()
            try {
                clientProperties = yaml.loadAs(reader, ClientProperties::class.java)!!
                logger.info(I18n["katana.server.file.load", propertiesFile.name])
            } catch (e: Exception) {
                reader.close()
                logger.info(I18n["katana.server.file.error", propertiesFile.name, e.toString()])
                logger.info(I18n["katana.server.file.backup", propertiesFile.name])
                val f = File(propertiesFile.name + ".${System.currentTimeMillis()}-old")
                propertiesFile.copyTo(f, true)
                propertiesFile.delete()
                loadClientProperties()
            }
        } else {
            try {
                clientProperties = ClientProperties()
                propertiesFile.createNewFile()
                yaml.dump(clientProperties, propertiesFile.writer())
                logger.info(I18n["katana.server.file.create", propertiesFile.name])
            } catch (e: Exception) {
                throw ClientException(e)
            }
        }
    }

    private fun saveClientProperties() {
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val yaml = Yaml(options)
        try {
            yaml.dump(clientProperties, propertiesFile.writer())
            logger.info(I18n["katana.server.file.save", propertiesFile.name])
        } catch (e: Exception) {
            throw ClientException(e)
        }
    }

    private fun updateLogger() {
        val ctx = LogManager.getContext(false) as LoggerContext
        val log4jConfig = ctx.configuration
        val loggerConfig = log4jConfig.getLoggerConfig(LogManager.ROOT_LOGGER_NAME)
        loggerConfig.level = Level.valueOf(clientProperties?.logLevel)
        ctx.updateLoggers()
    }

    private fun startMainThread() {
        Thread.currentThread().name = I18n["katana.client.thread.mainThread"]

        val tickRate = tickRate
        var now: Long
        var old: Long
        var diff = 0L
        var sleep: Long
        val rate = (1000 shl 16) / tickRate
        var sleepReal: Long
        while (state == ServerState.Running) {
            old = System.currentTimeMillis() shl 16

            if (update(totalTick++)) {
                val event = ClientUpdateTickEvent(this, totalTick - 1)
                eventManager(event, ClientUpdateTickEvent::class.java)
            }

            now = System.currentTimeMillis() shl 16
            sleep = rate - (now - old) - diff
            if (sleep < 0x20000) sleep = 0x20000
            old = now
            try {
                sleepReal = sleep shr 16
                Thread.sleep(sleepReal)
                if (sleepReal <= 2)
                    logger.debug(I18n["katana.client.warn.tickDelay"])
            } catch (e: Exception) {
                logger.warn("", e)
            }
            now = System.currentTimeMillis() shl 16
            diff = now - old - sleep
        }
    }
}