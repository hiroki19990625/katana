package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class ResourcePackDataInfoPacket : MinecraftPacket() {
    companion object {
        const val TYPE_INVALID = 0
        const val TYPE_ADDON = 1
        const val TYPE_CACHED = 2
        const val TYPE_COPY_PROTECTED = 3
        const val TYPE_BEHAVIOR = 4
        const val TYPE_PERSONA_PIECE = 5
        const val TYPE_RESOURCE = 6
        const val TYPE_SKINS = 7
        const val TYPE_WORLD_TEMPLATE = 8
        const val TYPE_COUNT = 9

        const val MB_10 = 10485760
        const val MB_1 = 1048576
        const val KB_512 = 524288
        const val KB_256 = 262144
    }

    override val packetId: Int = MinecraftProtocols.RESOURCE_PACK_DATA_INFO_PACKET

    var packId: String = ""
    var maxChunkSize: Int = MB_1
    var chunkCount: Int = 0
    var packSize: Long = 0
    var hash: ByteArray = ByteArray(0)
    var premium: Boolean = false
    var type: Int = TYPE_RESOURCE

    override fun decodePayload() {
        packId = readVarString()
        maxChunkSize = readIntLE()
        chunkCount = readIntLE()
        packSize = readLongLE()
        hash = read(readUnsignedVarInt())
        premium = readBoolean()
        type = readByte().toInt()
    }

    override fun encodePayload() {
        writeVarString(packId)
        writeIntLE(maxChunkSize)
        writeIntLE(chunkCount)
        writeLongLE(packSize)
        writeUnsignedVarInt(hash.size)
        write(*hash)
        writeBoolean(premium)
        writeByte(type)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }
}