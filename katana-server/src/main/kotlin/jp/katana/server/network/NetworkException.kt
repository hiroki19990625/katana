package jp.katana.server.network

import jp.katana.i18n.I18n

class NetworkException(exception: Exception) :
    RuntimeException(I18n["katana.server.network.exception", exception.toString()])