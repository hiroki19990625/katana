package jp.katana.server.network.packet.mcpe

class AvailableActorIdentifiersPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.AVAILABLE_ACTOR_IDENTIFIERS_PACKET

    var tag: ByteArray = ByteArray(0)

    override fun decodePayload() {
        tag = read(remaining())
    }

    override fun encodePayload() {
        write(*tag)
    }
}