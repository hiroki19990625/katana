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
                    readVarString(),
                    readVarString(),
                    readLongLE(),
                    readVarString(),
                    readVarString(),
                    readVarString(),
                    readBoolean(),
                    ByteArray(0)
                )
            )
        }
    }

    private fun writePacks(list: MutableList<IResourcePack>) {
        writeShortLE(list.size)
        for (pack in list) {
            writeVarString(pack.packId)
            writeVarString(pack.packVersion)
            writeLongLE(pack.packSize)

            writeVarString(pack.encryptionKey)
            writeVarString(pack.subPackName)
            writeVarString(pack.contentIdentity)

            writeBoolean(pack.unknownBool)
        }
    }
}