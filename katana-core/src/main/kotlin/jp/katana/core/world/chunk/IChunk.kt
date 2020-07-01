package jp.katana.core.world.chunk

import jp.katana.core.actor.IActor
import jp.katana.core.network.packet.mcpe.IMinecraftPacket
import jp.katana.core.world.IWorld
import jp.katana.math.Vector2Int

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

    fun getChunkPacket(): IMinecraftPacket
}