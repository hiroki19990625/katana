package jp.katana.server.network.packet.mcpe

import jp.katana.core.data.IResourcePack
import jp.katana.server.data.ResourcePack

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

        writePacks(behaviourPackEntries)
        writePacks(resourcePackEntries)
    }

    private fun readPacks(list: MutableList<IResourcePack>) {
        val len = readShortLE()
        for (i in 1..len) {
            list.add(
                ResourcePack(
                    null,
                    readString(),
                    readString(),
                    readLongLE(),
                    readString(),
                    readString(),
                    readString(),
                    readBoolean(),
                    ByteArray(0)
                )
            )
        }
    }

    private fun writePacks(list: MutableList<IResourcePack>) {
        writeShortLE(list.size)
        for (pack in list) {
            writeString(pack.packId)
            writeString(pack.packVersion)
            writeLongLE(pack.packSize)

            writeString(pack.encryptionKey)
            writeString(pack.subPackName)
            writeString(pack.contentIdentity)

            writeBoolean(pack.unknownBool)
        }
    }
}