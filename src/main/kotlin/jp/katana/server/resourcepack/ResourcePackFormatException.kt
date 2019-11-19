package jp.katana.server.resourcepack

import jp.katana.i18n.I18n
import java.io.IOException

class ResourcePackFormatException(name: String) : IOException(I18n["katana.server.resourcePack.malformed", name])