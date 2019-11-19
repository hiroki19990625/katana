package jp.katana.server

import io.netty.util.ResourceLeakDetector
import jp.katana.core.IServer
import jp.katana.core.IServerProperties
import jp.katana.core.ServerState
import jp.katana.core.actor.IActorDefinitions
import jp.katana.core.block.IBlockDefinitions
import jp.katana.core.command.ICommandSender
import jp.katana.core.event.IEventManager
import jp.katana.core.factory.IFactoryManager
import jp.katana.core.item.IItemDefinitions
import jp.katana.core.network.INetworkManager
import jp.katana.core.resourcepack.IResourcePackManager
import jp.katana.core.world.IWorldManager
import jp.katana.core.world.biome.IBiomeDefinitions
import jp.katana.i18n.I18n
import jp.katana.server.actor.ActorDefinitions
import jp.katana.server.block.BlockDefinitions
import jp.katana.server.command.ServerCommandSender
import jp.katana.server.console.KatanaConsole
import jp.katana.server.event.EventManager
import jp.katana.server.event.server.ServerStartEvent
import jp.katana.server.event.server.ServerStopEvent
import jp.katana.server.event.server.ServerUpdateTickEvent
import jp.katana.server.factory.CommandFactory
import jp.katana.server.factory.FactoryManager
import jp.katana.server.item.ItemDefinitions
import jp.katana.server.network.NetworkManager
import jp.katana.server.resourcepack.ResourcePackManager
import jp.katana.server.world.WorldManager
import jp.katana.server.world.biome.BiomeDefinitions
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LoggerContext
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.net.InetAddress

/**
 * katanaサーバーのインスタンスを提供します。
 * @property protocolVersion プロトコルのバージョン
 * @property gameVersion ゲームのバージョン
 * @property propertiesFile プロパティファイルの場所
 * @property serverProperties プロパティファイルのインスタンス
 * @property state サーバーのステータス
 * @property logger サーバーのロガー
 * @property console サーバーのコンソール
 * @property factoryManager データファクトリのマネージャー
 * @property eventManager イベントのマネージャー
 * @property networkManager ネットワークのマネージャー
 * @property serverPort サーバーのポート
 * @property serverAddress サーバーのアドレス
 * @property maxPlayer プレイヤーの最大人数
 * @property motd サーバーのタイトル
 * @property subMotd サーバーのサブタイトル
 * @property tickRate サーバーの更新レート
 * @property totalTick サーバーの起動時間
 * @property katanaConfigFile サーバーの設定ファイルの場所
 * @property katanaConfig サーバーの設定ファイルのインスタンス
 * @property serverCommandSender サーバーのコマンド実行者
 * @property consoleThread コンソールのスレッド
 * @property mainThread サーバーのメインスレッド
 */
class Server : IServer {
    companion object {
        init {
            System.setProperty("log4j.skipJansi", "false")
            System.setProperty("log4j.configurationFile", "log4j2-katana.xml")

            // TODO: Memory Leak Ignore
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED)

            Thread.currentThread().name = I18n["katana.server.thread.hostThread"]
        }

        const val PROTOCOL_VERSION = 388
        const val GAME_VERSION = "1.13.0"
        const val INTERNAL_GAME_VERSION = 17694723
    }

    override val protocolVersion: Int = PROTOCOL_VERSION
    override val gameVersion: String = GAME_VERSION

    override var propertiesFile: File = File("properties.yml")
    override var serverProperties: IServerProperties? = null
        private set
    override var state: ServerState = ServerState.Stopped
        private set
    override val logger = LogManager.getLogger(Server::class.java)!!
    override var console = KatanaConsole(this)
    override var factoryManager: IFactoryManager = FactoryManager(this)
    override var eventManager: IEventManager = EventManager()
    override var networkManager: INetworkManager? = null
        private set
    override var resourcePackManager: IResourcePackManager = ResourcePackManager(this)
    override var worldManager: IWorldManager? = null
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

    override val defineBlocks: IBlockDefinitions = BlockDefinitions()
    override val defineItems: IItemDefinitions = ItemDefinitions()
    override val defineActors: IActorDefinitions = ActorDefinitions()
    override val defineBiomes: IBiomeDefinitions = BiomeDefinitions()

    var networkManagerCreator: (Server) -> INetworkManager = { server -> NetworkManager(server) }

    var katanaConfigFile: File = File("katana.yml")
    var katanaConfig: KatanaConfig? = null

    private val serverCommandSender: ServerCommandSender = ServerCommandSender(this)

    private val consoleThread = Thread { startConsole() }
    private val mainThread = Thread { startMainThread() }

    /**
     * サーバーを開始します。
     */
    override fun start() {
        state = ServerState.Running
        consoleThread.name = I18n["katana.server.thread.consoleThread"]
        consoleThread.start()

        logger.info(I18n["katana.server.starting"])

        try {
            loadServerProperties()
            loadKatanaConfig()

            updateLogger()

            logger.info(I18n["katana.server.network.starting"])
            networkManager = networkManagerCreator(this)
            if (state == ServerState.Stopped)
                return
            networkManager?.start()
            logger.info(I18n["katana.server.network.startInfo", serverPort])

            worldManager = WorldManager(this)
            worldManager!!.loadDefaultWorld(serverProperties!!.levelName)
        } catch (e: Exception) {
            logger.error("", e)
            shutdownForce()
            return
        }

        val event = ServerStartEvent(this)
        eventManager(event, ServerStartEvent::class.java)

        mainThread.start()
    }

    /**
     * サーバーを終了します。
     * @return サーバーの終了に成功したかどうか
     */
    override fun shutdown(): Boolean {
        state = ServerState.Stopping
        logger.info(I18n["katana.server.stopping"])

        val event = ServerStopEvent(this)
        eventManager(event, ServerStopEvent::class.java)

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

    /**
     * サーバーを強制的に終了します。
     * @return サーバーの強制終了に成功したかどうか
     */
    override fun shutdownForce(): Boolean {
        state = ServerState.Stopped
        logger.info(I18n["katana.server.stop"])

        return true
    }

    /**
     * サーバーの更新時に呼び出される。
     * @param tick これまでの累計時間
     * @return 更新に成功したかどうか
     */
    override fun update(tick: Long): Boolean {
        return try {
            val command = console.readCommand()
            if (command != null) {
                executeCommand(serverCommandSender, command)
            }
            true
        } catch (e: Exception) {
            logger.warn("", e)
            false
        }
    }

    /**
     * コマンドを実行した場合に呼び出される。
     * @param sender コマンド実行者
     * @param command コマンド文字列
     */
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

    private fun loadServerProperties() {
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val yaml = Yaml(options)
        if (propertiesFile.isFile && propertiesFile.exists()) {
            val reader = propertiesFile.reader()
            try {
                serverProperties = yaml.loadAs(reader, ServerProperties::class.java)!!
                logger.info(I18n["katana.server.file.load", propertiesFile.name])
            } catch (e: Exception) {
                reader.close()
                logger.info(I18n["katana.server.file.error", propertiesFile.name, e.toString()])
                logger.info(I18n["katana.server.file.backup", propertiesFile.name])
                val f = File(propertiesFile.name + ".${System.currentTimeMillis()}-old")
                propertiesFile.copyTo(f, true)
                propertiesFile.delete()
                loadServerProperties()
            }
        } else {
            try {
                serverProperties = ServerProperties()
                propertiesFile.createNewFile()
                yaml.dump(serverProperties, propertiesFile.writer())
                logger.info(I18n["katana.server.file.create", propertiesFile.name])
            } catch (e: Exception) {
                throw ServerException(e)
            }
        }
    }

    private fun saveServerProperties() {
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val yaml = Yaml(options)
        try {
            yaml.dump(serverProperties, propertiesFile.writer())
            logger.info(I18n["katana.server.file.save", propertiesFile.name])
        } catch (e: Exception) {
            throw ServerException(e)
        }
    }

    private fun loadKatanaConfig() {
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val yaml = Yaml(options)
        if (katanaConfigFile.isFile && katanaConfigFile.exists()) {
            val reader = propertiesFile.reader()
            try {
                katanaConfig = yaml.loadAs(katanaConfigFile.reader(), KatanaConfig::class.java)!!
                logger.info(I18n["katana.server.file.load", katanaConfigFile.name])
            } catch (e: Exception) {
                reader.close()
                logger.info(I18n["katana.server.file.error", propertiesFile.name, e.toString()])
                logger.info(I18n["katana.server.file.backup", propertiesFile.name])
                val f = File(propertiesFile.name + ".${System.currentTimeMillis()}-old")
                propertiesFile.copyTo(f, true)
                propertiesFile.delete()
                loadKatanaConfig()
            }
        } else {
            try {
                katanaConfig = KatanaConfig()
                katanaConfigFile.createNewFile()
                yaml.dump(katanaConfig, katanaConfigFile.writer())
                logger.info(I18n["katana.server.file.create", katanaConfigFile.name])
            } catch (e: Exception) {
                throw ServerException(e)
            }
        }
    }

    private fun saveKatanaConfig() {
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val yaml = Yaml(options)
        try {
            yaml.dump(katanaConfig, katanaConfigFile.writer())
            logger.info(I18n["katana.server.file.save", katanaConfigFile.name])
        } catch (e: Exception) {
            throw ServerException(e)
        }
    }

    private fun updateLogger() {
        val ctx = LogManager.getContext(false) as LoggerContext
        val log4jConfig = ctx.configuration
        val loggerConfig = log4jConfig.getLoggerConfig(LogManager.ROOT_LOGGER_NAME)
        loggerConfig.level = Level.valueOf(katanaConfig!!.logLevel)
        ctx.updateLoggers()
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
                eventManager(event, ServerUpdateTickEvent::class.java)
            }

            now = System.currentTimeMillis() shl 16
            sleep = rate - (now - old) - diff
            if (sleep < 0x20000) sleep = 0x20000
            old = now
            try {
                sleepReal = sleep shr 16
                Thread.sleep(sleepReal)
                if (sleepReal <= 2)
                    logger.debug(I18n["katana.server.warn.tickDelay"])
            } catch (e: Exception) {
                logger.warn("", e)
            }
            now = System.currentTimeMillis() shl 16
            diff = now - old - sleep
        }
    }
}