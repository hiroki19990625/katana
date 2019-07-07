package jp.katana.server.command.defaults

import jp.katana.core.command.ICommandSender
import jp.katana.i18n.I18n
import jp.katana.server.Server
import jp.katana.server.command.KatanaCommand

class StopCommand(server: Server) : KatanaCommand(server, I18n["katana.server.command.stop.name"]) {
    override fun execute(sender: ICommandSender, label: String, args: List<String>): Boolean {
        server.shutdown()
        return true
    }
}