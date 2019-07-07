package jp.katana.server.command

import jp.katana.core.command.ICommand
import jp.katana.server.Server

abstract class Command(protected val server: Server, final override val prefix: String, final override val name: String) :
    ICommand {
    override val fullName: String = "$prefix:$name"
}