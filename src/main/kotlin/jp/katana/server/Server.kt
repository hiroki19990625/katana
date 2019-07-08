package jp.katana.server

import jp.katana.core.IServer
import jp.katana.core.IServerProperties
import jp.katana.core.ServerState
import jp.katana.core.command.ICommandSender
import jp.katana.core.event.IEventManager
import jp.katana.core.factory.IFactoryManager
import jp.katana.core.network.INetworkManager
import jp.katana.i18n.I18n
import jp.katana.server.command.ServerCommandSender
import jp.katana.server.console.KatanaConsole
import jp.katana.server.event.EventManager
import jp.katana.server.event.server.ServerStartEvent
import jp.katana.server.event.server.ServerStopEvent
import jp.katana.server.event.server.ServerUpdateTickEvent
import jp.katana.server.factory.CommandFactory
import jp.katana.server.factory.FactoryManager
import jp.katana.server.network.NetworkManager
import org.apache.logging.log4j.LogManager
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.net.InetAddress


class Server : IServer {
    companion object {
        init {
            System.setProperty("log4j.skipJansi", "false")
            System.setProperty("log4j.configurationFile", "log4j2-katana.xml")

            Thread.currentThread().name = I18n["katana.server.thread.hostThread"]
        }
    }

    override val protocolVersion: Int = 354
    override val gameVersion: String = "1.11.4"

    override val propertiesFile: File = File("properties.yml")
    override var serverProperties: IServerProperties? = null
        private set
    override var state: ServerState = ServerState.Stopped
        private set
    override val logger = LogManager.getLogger(Server::class.java)!!
    override val console = KatanaConsole(this)
    override val factoryManager: IFactoryManager = FactoryManager(this)
    override val eventManager: IEventManager = EventManager()
    override var networkManager: INetworkManager? = null
        private set

    override val serverPort: Int
        get() = serverProperties!!.serverPort
    override val serverAddress: InetAddress
        get() = InetAddress.getByName(serverProperties!!.serverIp)
    override var maxPlayer: Int
        get() = serverProperties!!.maxPlayer
        set(value) {
            serverProperties!!.maxPlayer = value
            logger.warn(I18n["katana.server.warn.changeMaxPlayer"])
        }
    override var motd: String
        get() = serverProperties!!.motd
        set(value) {
            serverProperties!!.motd = value
        }
    override var subMotd: String
        get() = serverProperties!!.subMotd
        set(value) {
            serverProperties!!.subMotd = value
        }
    override val tickRate: Byte
        get() = katanaConfig!!.serverTickRate
    override var totalTick: Long = 0
        private set

    private val katanaConfigFile: File = File("katana.yml")
    private var katanaConfig: KatanaConfig? = null

    private val serverCommandSender: ServerCommandSender = ServerCommandSender(this)

    private val consoleThread = Thread { startConsole() }
    private val mainThread = Thread { startMainThread() }

    override fun start() {
        state = ServerState.Running
        consoleThread.name = I18n["katana.server.thread.consoleThread"]
        consoleThread.start()

        logger.info(I18n["katana.server.starting"])

        try {
            loadServerProperties()
            loadKatanaConfig()

            logger.info(I18n["katana.server.network.starting"])
            networkManager = NetworkManager(this)
            if (state == ServerState.Stopped)
                return
            networkManager?.start()
            logger.info(I18n["katana.server.network.startInfo", serverPort])
        } catch (e: Exception) {
            logger.error(e)
            shutdownForce()
            return
        }

        val event = ServerStartEvent(this)
        eventManager(event)

        mainThread.start()
    }

    override fun shutdown(): Boolean {
        state = ServerState.Stopping
        logger.info(I18n["katana.server.stopping"])

        val event = ServerStopEvent(this)
        eventManager(event)

        try {
            saveServerProperties()
            saveKatanaConfig()

            networkManager?.shutdown()
        } catch (e: Exception) {
            logger.error("", e)
            return shutdownForce()
        }

        if (consoleThread.isAlive)
            consoleThread.interrupt()

        state = ServerState.Stopped
        logger.info(I18n["katana.server.stop"])
        return true
    }

    override fun shutdownForce(): Boolean {
        state = ServerState.Stopped
        logger.info(I18n["katana.server.stop"])
        return true
    }

    override fun update(tick: Long): Boolean {
        return try {
            val command = console.readCommand()
            if (command != null) {
                executeCommand(serverCommandSender, command)
            }
            true
        } catch (e: Exception) {
            logger.warn(e)
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
            logger.warn(e)
        }
    }

    private fun startConsole() {
        try {
            console.start()
        } catch (e: Exception) {
            logger.error(e)
            shutdownForce()
        }
    }

    private fun loadServerProperties() {
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val yaml = Yaml(options)
        if (propertiesFile.exists()) {
            serverProperties = yaml.loadAs(propertiesFile.reader(), ServerProperties::class.java)
            logger.info(I18n["katana.server.file.load", propertiesFile.name])
        } else {
            serverProperties = ServerProperties()
            propertiesFile.createNewFile()
            yaml.dump(serverProperties, propertiesFile.writer())
            logger.info(I18n["katana.server.file.create", propertiesFile.name])
        }
    }

    private fun saveServerProperties() {
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val yaml = Yaml(options)
        yaml.dump(serverProperties, propertiesFile.writer())
        logger.info(I18n["katana.server.file.save", propertiesFile.name])
    }

    private fun loadKatanaConfig() {
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val yaml = Yaml(options)
        if (katanaConfigFile.exists()) {
            katanaConfig = yaml.loadAs(katanaConfigFile.reader(), KatanaConfig::class.java)
            logger.info(I18n["katana.server.file.load", katanaConfigFile.name])
        } else {
            katanaConfig = KatanaConfig()
            katanaConfigFile.createNewFile()
            yaml.dump(katanaConfig, katanaConfigFile.writer())
            logger.info(I18n["katana.server.file.create", katanaConfigFile.name])
        }
    }

    private fun saveKatanaConfig() {
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val yaml = Yaml(options)
        yaml.dump(katanaConfig, katanaConfigFile.writer())
        logger.info(I18n["katana.server.file.save", katanaConfigFile.name])
    }

    private fun startMainThread() {
        Thread.currentThread().name = I18n["katana.server.thread.mainThread"]

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
                val event = ServerUpdateTickEvent(this, totalTick - 1)
                eventManager(event)
            }

            now = System.currentTimeMillis() shl 16
            sleep = rate - (now - old) - diff
            if (sleep < 0x20000) sleep = 0x20000
            old = now
            try {
                sleepReal = sleep shr 16
                Thread.sleep(sleepReal)
                if (sleepReal <= 2)
                    logger.warn(I18n["katana.server.warn.tickDelay"])
            } catch (e: Exception) {
                logger.warn(e)
            }
            now = System.currentTimeMillis() shl 16
            diff = now - old - sleep
        }
    }
}