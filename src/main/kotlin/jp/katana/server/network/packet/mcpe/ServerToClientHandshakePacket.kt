package jp.katana.server.network.packet.mcpe

class ServerToClientHandshakePacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.SERVER_TO_CLIENT_HANDSHAKE_PACKET

    var token: String = ""

    override fun decodePayload() {
        token = readString()
    }

    override fun encodePayload() {
        writeString(token)
    }
}