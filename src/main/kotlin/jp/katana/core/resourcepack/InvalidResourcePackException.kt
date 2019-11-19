package jp.katana.core.resourcepack

import jp.katana.i18n.I18n
import java.io.IOException

class InvalidResourcePackException(name: String) : IOException(I18n["katana.server.resourcePack.invalid", name])