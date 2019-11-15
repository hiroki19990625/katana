package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

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
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(ChunkRadiusUpdatedPacket::radius, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}