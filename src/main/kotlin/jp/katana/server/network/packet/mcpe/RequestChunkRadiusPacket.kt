package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.server.actor.ActorPlayer

class RequestChunkRadiusPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.REQUEST_CHUNK_RADIUS_PACKET

    var radius = 0

    override fun decodePayload() {
        radius = readVarInt()
    }

    override fun encodePayload() {
        writeVarInt(radius)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        if (player is ActorPlayer) {
            val chunkRadiusUpdatedPacket = ChunkRadiusUpdatedPacket()
            val maxRadius = server.serverProperties!!.viewDistance.toInt();
            if (radius > maxRadius) {
                chunkRadiusUpdatedPacket.radius = maxRadius
            } else {
                chunkRadiusUpdatedPacket.radius = radius
            }
            player.sendPacket(chunkRadiusUpdatedPacket)
        }
    }
}