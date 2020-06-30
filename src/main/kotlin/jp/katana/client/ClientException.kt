package jp.katana.client

import jp.katana.i18n.I18n

class ClientException(exception: Exception) :
    RuntimeException(I18n["katana.server.exception.critical", exception.toString()])