package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.utils.readBlockPosition
import jp.katana.core.utils.writeBlockPosition
import jp.katana.debug.appendIndent
import jp.katana.debug.appendProperty
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

    override fun handleServer(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(NetworkChunkPublisherUpdatePacket::position, this, indent + 1)
        builder.appendProperty(NetworkChunkPublisherUpdatePacket::radius, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}