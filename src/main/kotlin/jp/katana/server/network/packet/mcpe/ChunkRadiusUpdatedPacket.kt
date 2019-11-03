package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.server.actor.ActorPlayer

class ChunkRadiusUpdatedPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.CHUNK_RADIUS_UPDATED_PACKET

    var radius = 0

    override fun decodePayload() {
        radius = readVarInt()
    }

    override fun encodePayload() {
        writeVarInt(radius)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        if (player is ActorPlayer)
            player.chunkRadius = radius;
    }
}