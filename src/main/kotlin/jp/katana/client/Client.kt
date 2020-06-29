package jp.katana.client

import io.netty.util.ResourceLeakDetector
import jp.katana.client.console.KatanaConsole
import jp.katana.core.ClientState
import jp.katana.core.IClient
import jp.katana.core.IClientProperties
import jp.katana.core.command.ICommandSender
import jp.katana.core.console.IConsole
import jp.katana.i18n.I18n
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
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
    override val logger: Logger = LogManager.getLogger(Client::class.java)!!
    override val console: IConsole = KatanaConsole(this)

    override val serverPort: Int
        get() = clientProperties!!.serverPort
    override val serverAddress: InetAddress
        get() = InetAddress.getByName(clientProperties!!.serverIp)

    override val tickRate: Byte
        get() = clientProperties!!.tickRate
    override var totalTick: Long = 0
        private set

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun shutdown(): Boolean {
        TODO("Not yet implemented")
    }

    override fun shutdownForce(): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(tick: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun executeCommand(sender: ICommandSender, command: String) {
        TODO("Not yet implemented")
    }
}