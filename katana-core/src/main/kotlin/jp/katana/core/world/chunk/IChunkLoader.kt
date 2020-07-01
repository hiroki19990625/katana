package jp.katana.core.world.chunk

import jp.katana.math.Vector2Int

interface IChunkLoader {
    fun getLoaderId(): Long
    fun getRadius(): Int
    fun getChunkPosition(): Vector2Int
    fun getLoadedChunksMap(): MutableMap<Vector2Int, Double>
}