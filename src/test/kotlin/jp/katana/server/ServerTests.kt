package jp.katana.server

import org.junit.Test

class ServerTests {

    /**
     * サーバーが起動できる
     */
    @Test
    fun startServer() {
        val server = Server()
        server.start()
        Thread.sleep(1000)
        server.shutdown()
    }

    @Test
    fun startServerChangePort() {
        val server = Server()
        server.start()
        Thread.sleep(1000)
        server.serverProperties!!.serverPort = 19133
        server.shutdown()

        Thread.sleep(1000)

        val newServer = Server()
        newServer.start()
        Thread.sleep(1000)
        server.serverProperties!!.serverPort = 19132
        newServer.shutdown()
    }
}