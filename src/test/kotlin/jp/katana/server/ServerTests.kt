package jp.katana.server

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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

    /**
     * 異なるポートで開く
     */
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

    /**
     * 二重スタート
     */
    @Test
    fun invalidPort() {
        val server = Server()

        Assertions.assertThrows(IllegalThreadStateException::class.java, fun() {
            server.start()
            server.start()
        })

        server.shutdown()
    }
}