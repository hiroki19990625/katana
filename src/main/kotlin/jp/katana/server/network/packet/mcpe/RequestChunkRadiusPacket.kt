package jp.katana.server.network.packet.mcpe

class RequestChunkRadiusPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.REQUEST_CHUNK_RADIUS_PACKET

    var radius = 0

    override fun decodePayload() {
        radius = readVarInt()
    }

    override fun encodePayload() {
        writeVarInt(radius)
    }
}