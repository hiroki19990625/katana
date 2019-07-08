package jp.katana.server.data

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jp.katana.core.data.ILoginData
import java.util.*

class LoginData : ILoginData {
    override var xuid: String = ""
        private set
    override var displayName: String = ""
        private set
    override var clientUuid: UUID = UUID.randomUUID()
        private set
    override var publicKey: String = ""
        private set

    override fun decode(data: String) {
        val gson = Gson()
        val jobj = gson.fromJson(data, JsonObject::class.java)
    }

    override fun encode(): String {
        return ""
    }
}