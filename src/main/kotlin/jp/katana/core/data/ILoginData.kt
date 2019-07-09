package jp.katana.core.data

import java.security.PublicKey
import java.util.*

interface ILoginData {
    val xuid: String
    val displayName: String
    val clientUuid: UUID
    val publicKey: PublicKey?
    val jwtVerify: Boolean

    fun decode(data: String)
    fun encode(): String
}