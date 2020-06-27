package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.math.Vector2Int

class LevelChunkPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.LEVEL_CHUNK_PACKET

    var pos: Vector2Int = Vector2Int(0, 0)

    var subChunkCount: Int = 0
    var cacheEnabled: Boolean = false

    var blobIds: LongArray = LongArray(0)

    var data: ByteArray = ByteArray(0)

    override fun decodePayload() {
        pos.x = readVarInt()
        pos.y = readVarInt()

        subChunkCount = readUnsignedVarInt()

        cacheEnabled = readBoolean()
        if (cacheEnabled) {
            val len = readUnsignedVarInt()
            blobIds = LongArray(len)
            for (i in 0 until len) {
                blobIds[i] = readLongLE()
            }
        }

        val len = readUnsignedVarInt()
        data = read(len)
    }

    override fun encodePayload() {
        writeVarInt(pos.x)
        writeVarInt(pos.y)

        writeUnsignedVarInt(subChunkCount)

        writeBoolean(cacheEnabled)
        if (cacheEnabled) {
            writeUnsignedVarInt(blobIds.size)
            for (element in blobIds)
                writeLongLE(element)
        }

        writeUnsignedVarInt(data.size)
        write(data)
    }

    override fun handleServer(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(LevelChunkPacket::pos, this, indent + 1)
        builder.appendProperty(LevelChunkPacket::cacheEnabled, this, indent + 1)
        builder.appendLongArrayProperty(LevelChunkPacket::blobIds, this, indent + 1)
        builder.appendByteArrayProperty(LevelChunkPacket::data, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}