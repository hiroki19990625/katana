package jp.katana.core.data

import java.security.PublicKey

/**
 * プレイヤーのクライアントデータを実装します。
 */
interface IClientData {
    val clientRandomId: String
    val currentInputMode: Byte
    val defaultInputMode: Byte
    val deviceId: String
    val deviceModel: String
    val deviceOS: Byte
    val gameVersion: String
    val guiScale: Byte
    val languageCode: String
    val platformOfflineId: String
    val platformOnlineId: String
    val premiumSkin: Boolean
    val selfSignedId: String
    val serverAddress: String
    val skin: ISkin?
    val thirdPartyName: String
    val uiProfile: String

    /**
     * データを解析します。
     * @param data 解析するデータ
     */
    fun decode(publicKey: PublicKey, data: String)

    /**
     * データを作成します。
     * @return 作成されたデータ
     */
    fun encode(): String
}