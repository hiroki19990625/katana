package jp.katana.core.world.chunk

import jp.katana.core.actor.IActor
import jp.katana.core.world.IWorld
import jp.katana.math.Vector2Int
import jp.katana.server.network.packet.mcpe.MinecraftPacket

interface IChunk {
    companion object {
        const val MAX_HEIGHT = 256
        const val MAX_COLUMN_HEIGHT = 16

        const val XZ = 256
    }

    val world: IWorld
    val pos: Vector2Int

    val maxHeight: Int
    val maxColumnHeight: Int

    val columns: Array<ISubChunk>

    val biomes: ByteArray

    fun getActors(): Array<IActor>

    fun getChunkPacket(): MinecraftPacket
}