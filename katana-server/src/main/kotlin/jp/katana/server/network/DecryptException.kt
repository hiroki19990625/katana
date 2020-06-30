package jp.katana.server.network

import jp.katana.i18n.I18n

class DecryptException(exception: Exception) :
    RuntimeException(I18n["katana.server.network.decrypt.exception", exception.toString()])