package jp.katana.server.factory

import jp.katana.core.command.ICommand

class CommandFactory : SimpleFactory<String, ICommand>() {
    init {

    }

    override operator fun plusAssign(value: ICommand) {
        if (!map.containsKey(value.name))
            map[value.name] = value
        if (!map.containsKey(value.fullName))
            map[value.fullName] = value
    }
}