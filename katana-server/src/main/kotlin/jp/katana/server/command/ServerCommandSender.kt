package jp.katana.server.command

import jp.katana.core.command.ICommandSender
import jp.katana.server.Server

class ServerCommandSender(private val server: Server) : ICommandSender {
    override fun sendMessage(msg: String) {
        server.logger.info(msg)
    }
}