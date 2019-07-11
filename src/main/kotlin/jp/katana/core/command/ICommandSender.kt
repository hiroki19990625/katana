package jp.katana.core.command

/**
 * コマンドの実行者を実装します。
 */
interface ICommandSender {
    /**
     * コマンドの実行者にメッセージを送信します。
     * @param msg メッセージ
     */
    fun sendMessage(msg: String)
}