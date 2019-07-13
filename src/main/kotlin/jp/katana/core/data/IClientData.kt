package jp.katana.core.data

import java.security.PublicKey

/**
 * プレイヤーのクライアントデータを実装します。
 */
interface IClientData {
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