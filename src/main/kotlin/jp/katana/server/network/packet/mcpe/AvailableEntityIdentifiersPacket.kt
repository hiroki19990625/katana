package jp.katana.server.network.packet.mcpe

class AvailableEntityIdentifiersPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.AVAILABLE_ENTITY_IDENTIFIERS_PACKET

    var tag: ByteArray = ByteArray(0)

    override fun decodePayload() {
        tag = read(remaining())
    }

    override fun encodePayload() {
        write(*tag)
    }
}