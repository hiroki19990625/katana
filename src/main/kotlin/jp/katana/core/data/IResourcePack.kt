package jp.katana.core.data

interface IResourcePack {
    val packId: String
    val packVersion: String
    val packSize: Long

    val encryptionKey: String
    val subPackName: String
    val contentIdentity: String

    val unknownBool: Boolean
}