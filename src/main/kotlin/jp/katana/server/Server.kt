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

    private val console = KatanaConsole(this)
    private val consoleThread = Thread { startConsole() }

    override fun start() {
        state = ServerState.Running
        consoleThread.start()

        try {
            loadServerProperties()
        } catch (e: Exception) {
            return
        }

        while (state == ServerState.Running) {
            Thread.sleep(1000)
            logger.info("update")
        }
    }

    override fun shutdown(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun shutdownForce(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun startConsole() {
        try {
            console.start()
        } catch (e: Exception) {
            logger.error(e)
        }
    }

    private fun loadServerProperties() {

    }
}