package jp.katana.core.data

interface IClientData {

    fun decode(data: String)
    fun encode(): String
}