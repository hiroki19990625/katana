package jp.katana.client.command

import jp.katana.client.Client
import jp.katana.core.command.ICommandSender

class ClientCommandSender(private val client: Client) : ICommandSender {
    override fun sendMessage(msg: String) {
        client.logger.info(msg)
    }
}