package jp.katana.server.factory

import jp.katana.core.command.ICommand
import jp.katana.server.Server
import jp.katana.server.command.defaults.StopCommand

class CommandFactory(private val server: Server) : SimpleFactory<String, ICommand>() {
    init {
        this += StopCommand(server)
    }

    override operator fun plusAssign(value: ICommand) {
        if (!map.containsKey(value.name))
            map[value.name] = value
        if (!map.containsKey(value.fullName))
            map[value.fullName] = value
    }
}