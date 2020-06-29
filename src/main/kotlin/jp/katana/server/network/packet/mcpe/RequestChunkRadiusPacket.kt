package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.actor.PlayerState
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

    override fun handleServer(player: IActorPlayer, server: IServer) {
        if (player is ActorPlayer) {
            val chunkRadiusUpdatedPacket = ChunkRadiusUpdatedPacket()
            val maxRadius = server.serverProperties!!.viewDistance.toInt()
            if (radius > maxRadius) {
                chunkRadiusUpdatedPacket.radius = maxRadius
            } else {
                chunkRadiusUpdatedPacket.radius = radius
            }
            player.chunkRadius = chunkRadiusUpdatedPacket.radius
            player.sendPacket(chunkRadiusUpdatedPacket)


            player.world!!.registerChunkLoader(player)
            player.world!!.sendChunks(player)

            val playStatusPacket = PlayStatusPacket()
            playStatusPacket.status = PlayStatusPacket.PLAYER_SPAWN
            player.sendPacket(playStatusPacket)

            player.state = PlayerState.Spawned
        }
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(RequestChunkRadiusPacket::radius, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}