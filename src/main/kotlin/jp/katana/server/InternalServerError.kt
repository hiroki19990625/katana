package jp.katana.server

import jp.katana.i18n.I18n

class InternalServerError : Error(I18n["katana.server.error.critical"])