package jp.katana.server.network.packet.mcpe

import jp.katana.core.data.IResourcePackInfo
import jp.katana.server.data.ResourcePackInfo


class ResourcePackStackPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.RESOURCE_PACK_STACK_PACKET

    var mustAccept = false
    var behaviourPackStack = mutableListOf<IResourcePackInfo>()
    var resourcePackStack = mutableListOf<IResourcePackInfo>()
    var isExperimental = false

    override fun decodePayload() {
        mustAccept = readBoolean()

        readPacks(behaviourPackStack)
        readPacks(resourcePackStack)

        isExperimental = readBoolean()
    }

    override fun encodePayload() {
        writeBoolean(mustAccept)

        writePacks(behaviourPackStack)
        writePacks(resourcePackStack)

        writeBoolean(isExperimental)
    }

    private fun readPacks(list: MutableList<IResourcePackInfo>) {
        val len = readUnsignedVarInt()
        for (pack in 1..len) {
            list.add(ResourcePackInfo(null, readVarString(), readVarString(), 0, "", readVarString(), "", false, ByteArray(0)))
        }
    }

    private fun writePacks(list: MutableList<IResourcePackInfo>) {
        writeUnsignedInt(list.size.toLong())
        for (pack in list) {
            writeVarString(pack.packId)
            writeVarString(pack.packVersion)
            writeVarString(pack.subPackName)
        }
    }
}