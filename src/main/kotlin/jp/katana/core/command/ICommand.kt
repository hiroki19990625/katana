package jp.katana.core.command

interface ICommand {
    val fullName: String
    val prefix: String
    val name: String

    fun execute(sender: ICommandSender, label: String, args: List<String>): Boolean
}