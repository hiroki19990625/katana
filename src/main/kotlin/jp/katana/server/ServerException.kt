package jp.katana.server

import jp.katana.i18n.I18n

class ServerException(exception: Exception) :
    RuntimeException(I18n["katana.server.exception.critical", exception.toString()])