package jp.katana.server.network.packet.mcpe

class ServerToClientHandshakePacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.SERVER_TO_CLIENT_HANDSHAKE_PACKET

    var token: String = ""

    override fun decodePayload() {
        token = readVarString()
    }

    override fun encodePayload() {
        writeVarString(token)
    }
}