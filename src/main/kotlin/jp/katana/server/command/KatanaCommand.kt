package jp.katana.server.command

import jp.katana.i18n.I18n
import jp.katana.server.Server

abstract class KatanaCommand(server: Server, name: String) :
    Command(server, I18n["katana.server.command.katana.prefix"], name)