package jp.katana.server.network.packet.mcpe

class ResourcePackChunkRequestPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.RESOURCE_PACK_CHUNK_REQUEST_PACKET

    var packId: String = ""
    var chunkIndex: Int = 0

    override fun decodePayload() {
        packId = readString()
        chunkIndex = readIntLE()
    }

    override fun encodePayload() {
        writeString(packId)
        writeIntLE(chunkIndex)
    }
}