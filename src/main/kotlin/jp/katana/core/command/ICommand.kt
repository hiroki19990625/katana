package jp.katana.core.command

interface ICommand {
    val fullName: String
    val prefix: String
    val name: String
}