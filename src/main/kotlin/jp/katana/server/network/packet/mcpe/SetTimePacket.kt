package jp.katana.server.network.packet.mcpe

class SetTimePacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.SET_TIME_PACKET

    var time = 0

    override fun decodePayload() {
        time = readVarInt()
    }

    override fun encodePayload() {
        writeVarInt(time)
    }
}