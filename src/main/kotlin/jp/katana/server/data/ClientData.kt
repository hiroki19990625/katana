package jp.katana.server.data

import com.nimbusds.jose.JWSObject
import jp.katana.core.data.IClientData
import jp.katana.core.data.ISkin
import jp.katana.i18n.I18n
import jp.katana.utils.Jwt
import java.nio.charset.StandardCharsets
import java.security.PublicKey
import java.util.*

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
        selfSignedId = obj.getAsString("SelfSignedId")
        serverAddress = obj.getAsString("ServerAddress")
        //TODO: Animation
        skin = Skin(
            SkinImage.create(obj, "Cape"),
            SkinImage.create(obj, "Skin"),
            String(Base64.getDecoder().decode(obj.getAsString("SkinGeometryData")), StandardCharsets.UTF_8),
            if (obj.containsKey("AnimationData")) String(
                Base64.getDecoder().decode(obj.getAsString("AnimationData")),
                StandardCharsets.UTF_8
            ) else "",
            String(Base64.getDecoder().decode(obj.getAsString("SkinResourcePatch")), StandardCharsets.UTF_8),
            obj.getAsString("SkinId"),
            obj.getAsString("CapeId"),
            obj.getAsString("PremiumSkin")!!.toBoolean(),
            obj.getAsString("PersonaSkin")!!.toBoolean(),
            obj.getAsString("CapeOnClassicSkin")!!.toBoolean()
        )
        thirdPartyName = obj.getAsString("ThirdPartyName")
        uiProfile = obj.getAsString("UIProfile")
    }

    override fun encode(): String {
        return ""
    }
}