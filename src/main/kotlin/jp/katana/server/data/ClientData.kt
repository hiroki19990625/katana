package jp.katana.server.data

import com.nimbusds.jose.JWSObject
import jp.katana.core.data.IClientData
import jp.katana.core.data.ISkin
import jp.katana.i18n.I18n
import jp.katana.server.utils.Jwt
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

        val obj = jwt.payload.toJSONObject()
        clientRandomId = obj.getAsString("ClientRandomId")
        currentInputMode = obj.getAsNumber("CurrentInputMode").toByte()
        defaultInputMode = obj.getAsNumber("DefaultInputMode").toByte()
        deviceId = obj.getAsString("DeviceId");
        deviceModel = obj.getAsString("DeviceModel")
        deviceOS = obj.getAsNumber("DeviceOS").toByte()
        gameVersion = obj.getAsString("GameVersion")
        guiScale = obj.getAsNumber("GuiScale").toByte()
        languageCode = obj.getAsString("LanguageCode")
        platformOfflineId = obj.getAsString("PlatformOfflineId")
        platformOnlineId = obj.getAsString("PlatformOnlineId")
        premiumSkin = obj.getAsString("PremiumSkin")!!.toBoolean()
        selfSignedId = obj.getAsString("SelfSignedId")
        serverAddress = obj.getAsString("ServerAddress")
        skin = Skin(
            obj.getAsString("CapeData"),
            obj.getAsString("SkinData"),
            obj.getAsString("SkinGeometry"),
            obj.getAsString("SkinGeometryName"),
            obj.getAsString("SkinId")
        )
        thirdPartyName = obj.getAsString("ThirdPartyName")
        uiProfile = obj.getAsString("UIProfile")
    }

    override fun encode(): String {
        return ""
    }
}