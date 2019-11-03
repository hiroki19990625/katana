package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class BiomeDefinitionListPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.BIOME_DEFINITION_LIST_PACKET

    var tag: ByteArray = ByteArray(0)

    override fun decodePayload() {
        tag = read(remaining())
    }

    override fun encodePayload() {
        write(*tag)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }
}