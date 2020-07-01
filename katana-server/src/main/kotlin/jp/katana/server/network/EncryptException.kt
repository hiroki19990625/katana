package jp.katana.server.network

import jp.katana.i18n.I18n

class EncryptException(exception: Exception) :
    RuntimeException(I18n["katana.server.network.encrypt.exception", exception.toString()])