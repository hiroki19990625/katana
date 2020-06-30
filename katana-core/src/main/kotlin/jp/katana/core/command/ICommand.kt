package jp.katana.core.command

/**
 * コマンドの基本パターンを実装します。
 * @property fullName コマンドの完全名
 * @property prefix コマンドのプレフィックス名
 * @property name コマンドの名前
 */
interface ICommand {
    val fullName: String
    val prefix: String
    val name: String

    /**
     * コマンド実行時に呼び出されます。(コマンドのロジックを記述します)
     * @param sender コマンドの実行者
     * @param label コマンドのラベル
     * @param args コマンドの引数
     * @return コマンドの結果
     */
    fun execute(sender: ICommandSender, label: String, args: List<String>): Boolean
}