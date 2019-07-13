package jp.katana.server.data

import com.nimbusds.jose.JWSObject
import jp.katana.core.data.IClientData
import jp.katana.i18n.I18n
import jp.katana.server.utils.Jwt
import java.security.PublicKey

class ClientData : IClientData {
    override fun decode(publicKey: PublicKey, data: String) {
        val jwt = JWSObject.parse(data)
        if (!Jwt.verify(publicKey, jwt)) {
            throw RuntimeException(I18n["katana.server.exception.jwtVerify"])
        }
    }

    override fun encode(): String {
        return ""
    }
}