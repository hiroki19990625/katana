package jp.katana.server.data

import com.nimbusds.jose.JWSObject
import jp.katana.core.data.IClientData
import jp.katana.core.data.ISkin
import jp.katana.i18n.I18n
import jp.katana.server.utils.Jwt
import org.apache.logging.log4j.LogManager
import java.security.PublicKey

class ClientData : IClientData {
    override var clientRandomId: String = ""
        private set
    override var currentInputMode: Byte = 0
        private set
    override var defaultInputMode: Byte = 0
        private set
    override var deviceId: String = ""
        private set
    override var deviceModel: String = ""
        private set
    override var deviceOS: Byte = 0
        private set
    override var gameVersion: String = ""
        private set
    override var guiScale: Byte = 0
        private set
    override var languageCode: String = ""
        private set
    override var platformOfflineId: String = ""
        private set
    override var platformOnlineId: String = ""
        private set
    override var premiumSkin: Boolean = false
        private set
    override var selfSignedId: String = ""
        private set
    override var serverAddress: String = ""
        private set
    override var skin: ISkin? = null
        private set
    override var thirdPartyName: String = ""
        private set
    override var uiProfile: String = ""
        private set

    override fun decode(publicKey: PublicKey, data: String) {
        val jwt = JWSObject.parse(data)
        if (!Jwt.verify(publicKey, jwt)) {
            throw RuntimeException(I18n["katana.server.exception.jwtVerify"])
        }

        LogManager.getLogger().info(jwt.payload)
    }

    override fun encode(): String {
        return ""
    }
}