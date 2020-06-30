package jp.katana

import jp.katana.client.Client
import jp.katana.i18n.I18n
import jp.katana.server.Server

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            if (args.isEmpty()) {
                val server = Server()
                server.logger.info(I18n["katana.selector.startServer"])

                try {
                    server.start()
                } catch (e: Throwable) {
                    server.logger.fatal("", e)
                    readLine()
                }
            } else {
                val type = args[0]
                if (type == "client") {
                    val client = Client()
                    client.logger.info(I18n["katana.selector.startClient"])

                    try {
                        client.start()
                    } catch (e: Throwable) {
                        client.logger.fatal("", e)
                        readLine()
                    }
                } else if (type == "server") {
                    val server = Server()
                    server.logger.info(I18n["katana.selector.startServer"])

                    try {
                        server.start()
                    } catch (e: Throwable) {
                        server.logger.fatal("", e)
                        readLine()
                    }
                }
            }
        }
    }
}