package jp.katana.server.network.packet.mcpe

class ClientToServerHandshakePacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.CLIENT_TO_SERVER_HANDSHAKE_PACKET
}
