package jp.katana.core.console

/**
 * コンソールを実装します。
 */
interface IConsole {
    /**
     * 入力されたコマンドを読み取ります。
     * @return 読み取られたコマンド
     */
    fun readCommand(): String?
}