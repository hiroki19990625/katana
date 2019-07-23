package jp.katana.server.data

import jp.katana.core.data.IResourcePack
import java.io.File

class ResourcePack(
    override val file: File?,
    override val packId: String,
    override val packVersion: String,
    override val packSize: Long,
    override val encryptionKey: String,
    override val subPackName: String,
    override val contentIdentity: String,
    override val unknownBool: Boolean,
    override val hash: ByteArray
) : IResourcePack