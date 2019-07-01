package jp.katana.server

import jp.katana.core.IServer
import jp.katana.core.ServerState
import jp.katana.server.console.KatanaConsole
import org.apache.logging.log4j.LogManager
import java.io.File

class Server : IServer {
    override val propertiesFile: File = File("properties.yml")
    override var state: ServerState = ServerState.Stopped
        private set
    override val logger = LogManager.getLogger(Server::class.java)!!

    private val console: KatanaConsole = KatanaConsole(this)

    init {
        state = ServerState.Running
        console.start()
        try {

        } catch (e: Exception) {

        }
    }

    override fun shutdownForce(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun shutdown(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loadServerProperties() {

    }
}