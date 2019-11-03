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
            for (i in 0 until blobIds.size)
                writeLongLE(blobIds[i])
        }

        writeUnsignedVarInt(data.size)
        write(*data)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }
}