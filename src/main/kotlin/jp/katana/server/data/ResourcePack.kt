package jp.katana.server.data

import jp.katana.core.data.IResourcePack

class ResourcePack(
    override val packId: String,
    override val packVersion: String,
    override val packSize: Long,
    override val encryptionKey: String,
    override val subPackName: String,
    override val contentIdentity: String,
    override val unknownBool: Boolean
) : IResourcePack