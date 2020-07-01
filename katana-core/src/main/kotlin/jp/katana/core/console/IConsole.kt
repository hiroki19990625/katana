package jp.katana.core.console

/**
 * コンソールを実装します。
 */
interface IConsole {
    /**
     * コマンドを実行します。
     * @param command コマンド
     */
    fun runCommand(command: String?)

    /**
     * 入力されたコマンドを読み取ります。
     * @return 読み取られたコマンド
     */
    fun readCommand(): String?
}