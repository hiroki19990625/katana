package jp.katana.server.network.packet.mcpe

class BiomeDefinitionListPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.BIOME_DEFINITION_LIST_PACKET

    var tag: ByteArray = ByteArray(0)

    override fun decodePayload() {
        tag = read(remaining())
    }

    override fun encodePayload() {
        write(*tag)
    }
}