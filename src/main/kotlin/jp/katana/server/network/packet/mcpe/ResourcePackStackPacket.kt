package jp.katana.server.network.packet.mcpe

import jp.katana.core.data.IResourcePack
import jp.katana.server.data.ResourcePack


class ResourcePackStackPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.RESOURCE_PACK_STACK_PACKET

    var mustAccept = false
    var behaviourPackStack = mutableListOf<IResourcePack>()
    var resourcePackStack = mutableListOf<IResourcePack>()
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

    private fun readPacks(list: MutableList<IResourcePack>) {
        val len = readUnsignedVarInt()
        for (pack in 1..len) {
            list.add(ResourcePack(null, readVarString(), readVarString(), 0, "", readVarString(), "", false, ByteArray(0)))
        }
    }

    private fun writePacks(list: MutableList<IResourcePack>) {
        writeUnsignedInt(list.size.toLong())
        for (pack in list) {
            writeVarString(pack.packId)
            writeVarString(pack.packVersion)
            writeVarString(pack.subPackName)
        }
    }
}