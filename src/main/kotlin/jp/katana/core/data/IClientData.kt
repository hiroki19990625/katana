package jp.katana.core.data

/**
 * プレイヤーのクライアントデータを実装します。
 */
interface IClientData {
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