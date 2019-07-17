package jp.katana.server.network.packet.mcpe

import jp.katana.core.data.IResourcePack

class ResourcePacksInfoPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.RESOURCE_PACKS_INFO_PACKET

    var mustAccept: Boolean = false
    var unknownBool: Boolean = false
    var behaviourPackEntries = mutableListOf<IResourcePack>()
    var resourcePackEntries = mutableListOf<IResourcePack>()

    override fun decodePayload() {
        mustAccept = readBoolean()
        unknownBool = readBoolean()
        readPacks(behaviourPackEntries)
        readPacks(resourcePackEntries)
    }

    override fun encodePayload() {
        writeBoolean(mustAccept)
        writeBoolean(unknownBool)

    }

    private fun readPacks(list: MutableList<IResourcePack>) {
        val len = readShortLE()
        for (i in 0..len) {
            list.add(object : IResourcePack {
                override val packId: String = readString()
                override val packVersion: String = readString()
                override val packSize: Long = readLongLE()

                override val encryptionKey: String = readString()
                override val subPackName: String = readString()
                override val contentIdentity: String = readString()

                override val unknownBool: Boolean = readBoolean()
            })
        }
    }

    private fun writePacks(list: MutableList<IResourcePack>) {

    }
}