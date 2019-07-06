package jp.katana.server.command

import jp.katana.core.command.ICommand

class Command : ICommand {
    constructor(prefix: String, name: String) {
        fullName = "$prefix:$name"
        this.prefix = prefix
        this.name = name
    }

    override val fullName: String
    override val prefix: String
    override val name: String
}