package jp.katana.server.world.chunk

import jp.katana.core.actor.IActor
import jp.katana.core.network.packet.mcpe.IMinecraftPacket
import jp.katana.core.world.IWorld
import jp.katana.core.world.chunk.IChunk
import jp.katana.core.world.chunk.ISubChunk
import jp.katana.math.Vector2Int
import jp.katana.server.network.packet.mcpe.LevelChunkPacket
import jp.katana.utils.BinaryStream
import java.util.*

class Chunk(override val world: IWorld, override val pos: Vector2Int) : IChunk {
    override val maxHeight: Int = IChunk.MAX_HEIGHT
    override val maxColumnHeight: Int = IChunk.MAX_COLUMN_HEIGHT
    override val columns: Array<ISubChunk> = Array(maxColumnHeight) { i -> SubChunk(i) }
    override val biomes: ByteArray = ByteArray(IChunk.XZ)

    private val actors: MutableMap<UUID, IActor> = mutableMapOf()

    override fun getActors(): Array<IActor> {
        return actors.values.toTypedArray()
    }

    override fun getChunkPacket(): IMinecraftPacket {
        val packet = LevelChunkPacket()

        var sendChunk = 16

        for (i in 15 downTo 0) {
            if (columns[i].isEmpty())
                sendChunk = i
            else
                break
        }

        val stream = BinaryStream()
        for (i in 0 until sendChunk) {
            stream.write(columns[i].networkSerialize())
        }

        stream.write(biomes)
        stream.writeByte(0) // Border

        // TODO: BlockEntity

        packet.pos = pos
        packet.subChunkCount = sendChunk
        packet.data = stream.array()

        stream.close()

        return packet
    }
}