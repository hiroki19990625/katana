package jp.katana.server.data

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.nimbusds.jose.JWSObject
import jp.katana.core.data.ILoginData
import jp.katana.i18n.I18n
import jp.katana.utils.Jwt
import java.security.PublicKey
import java.util.*

class LoginData : ILoginData {
    override var xuid: String = ""
        private set
    override var displayName: String = ""
        private set
    override var clientUuid: UUID = UUID.randomUUID()
        private set
    override var publicKey: PublicKey? = null
        private set
    override var jwtVerify: Boolean = false
        private set

    override fun decode(data: String) {
        val gson = Gson()
        val jobj = gson.fromJson(data, JsonObject::class.java)
        val chains = jobj["chain"] as JsonArray

        var mojangVerify = false
        var lastKey: PublicKey? = null
        for (chain in chains) {
            if (chain is JsonPrimitive && chain.isString) {
                val jwt = JWSObject.parse(chain.asString)
                if (!mojangVerify) {
                    mojangVerify = Jwt.verify(Jwt.mojangPublicKey, jwt)
                }

                if (lastKey != null && !Jwt.verify(lastKey, jwt)) {
                    throw RuntimeException(I18n["katana.server.exception.jwtVerify"])
                }

                val obj = jwt.payload.toJSONObject()
                val exData = obj["extraData"]
                if (exData != null && exData is JsonObject) {
                    xuid = exData["XUID"].asString
                    displayName = exData["displayName"].asString
                    clientUuid = UUID.fromString(exData["identity"].asString)
                }
                val base64Key = obj.getAsString("identityPublicKey") ?: throw RuntimeException("error")
                lastKey = Jwt.genECKey(base64Key)
            }

            publicKey = lastKey
            jwtVerify = mojangVerify
        }
    }

    override fun encode(): String {
        return ""
    }
}