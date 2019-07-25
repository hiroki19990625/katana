package jp.katana.server

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ServerStartTest {

    /**
     * サーバーが起動できる
     */
    @Test
    fun startServer() {
        val server = Server()
        server.start()
        Thread.sleep(2000)
        server.shutdown()
    }
}