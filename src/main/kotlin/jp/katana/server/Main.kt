package jp.katana.server

import jp.katana.client.Client
import jp.katana.i18n.I18n
import org.apache.logging.log4j.Logger

class Main {
    companion object {
        private var logger: Logger? = null

        @JvmStatic
        fun main(args: Array<String>) {
            if (args.isEmpty()) {
                val server = Server()
                server.logger.info(I18n["katana.selector.startServer"])
                server.start()
            } else {
                val type = args[0]
                if (type == "client") {
                    val client = Client()
                    client.logger.info(I18n["katana.selector.startServer"])
                    client.start()
                } else if (type == "server") {
                    val server = Server()
                    server.logger.info(I18n["katana.selector.startServer"])
                    server.start()
                }
            }
        }
    }
}