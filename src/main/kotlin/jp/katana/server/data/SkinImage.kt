package jp.katana.server.data

import jp.katana.core.data.ISkinImage
import net.minidev.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*

class SkinImage() : ISkinImage {
    companion object {
        val empty = SkinImage()

        fun create(token: JSONObject, name: String): SkinImage {
            val image = SkinImage()
            image.decode(token, name)
            return image
        }
    }

    override var width: Int = 0
        private set
    override var height: Int = 0
        private set
    override var data: String = ""
        private set

    constructor(w: Int, h: Int, d: String) : this() {
        width = w
        height = h
        data = d
    }

    override fun decode(token: JSONObject, name: String) {
        width = token.getAsNumber("${name}ImageWidth").toInt()
        height = token.getAsNumber("${name}ImageHeight").toInt()
        data = String(Base64.getDecoder().decode(token.getAsString("${name}Data")), StandardCharsets.UTF_8)
    }

    override fun encode(): String {
        return ""
    }
}