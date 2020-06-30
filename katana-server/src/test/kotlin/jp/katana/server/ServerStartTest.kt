package jp.katana.server

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class ServerStartTest {
    @BeforeEach
    fun beforeEach() {
        File("./properties.yml").delete()
        File("./katana.yml").delete()
        File("./logs").delete()
        File("./resource_packs").delete()
    }

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