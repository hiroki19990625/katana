package jp.katana.core.data

import java.util.*

interface ILoginData {
    val xuid: String
    val displayName: String
    val clientUuid: UUID
    val publicKey: String

    fun decode(data: String)
    fun encode(): String
}