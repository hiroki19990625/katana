package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.math.Vector3Int

class NetworkChunkPublisherUpdatePacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.NETWORK_CHUNK_PUBLISHER_UPDATE_PACKET

    var position: Vector3Int = Vector3Int(0, 0, 0)
    var radius: Int = 0

    override fun decodePayload() {
        position = readBlockPosition()
        radius = readUnsignedVarInt()
    }

    override fun encodePayload() {
        writeBlockPosition(position)
        writeUnsignedVarInt(radius)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.append("${this.javaClass.simpleName} {\n")
        builder.append("}\n")
    }
}