package jp.katana.core.data

import java.security.PublicKey
import java.util.*

/**
 * プレイヤーのログインデータを実装します。
 * @property xuid XboxのID
 * @property displayName プレイヤーの表示名
 * @property clientUuid プレイヤーのユニークなID
 * @property publicKey プレイヤーの公開鍵
 * @property jwtVerify Jwtの検証
 */
interface ILoginData {
    val xuid: String
    val displayName: String
    val clientUuid: UUID
    val publicKey: PublicKey?
    val jwtVerify: Boolean

    /**
     * データを解析します。
     * @param data 解析するデータ
     */
    fun decode(data: String)

    /**
     * データを作成します。
     * @return 作成されたデータ
     */
    fun encode(): String
}