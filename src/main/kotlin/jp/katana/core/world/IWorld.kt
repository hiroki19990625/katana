package jp.katana.core.world

import jp.katana.core.world.chunk.IChunk
import jp.katana.core.world.chunk.IChunkLoader
import jp.katana.core.world.gamerule.IGameRules
import jp.katana.math.Vector2Int
import jp.katana.math.Vector3Int

interface IWorld {
    val name: String
    val gameRules: IGameRules

    fun getChunk(x: Int, z: Int, useShift: Boolean = true): IChunk
    fun getChunk(pos: Vector2Int, useShift: Boolean = true): IChunk
    fun getChunk(x: Int, y: Int, z: Int, useShift: Boolean = true): IChunk
    fun getChunk(pos: Vector3Int, useShift: Boolean = true): IChunk

    fun loadChunk(x: Int, z: Int, useShift: Boolean = true): IChunk
    fun loadChunk(pos: Vector2Int, useShift: Boolean = true): IChunk
    fun loadChunk(x: Int, y: Int, z: Int, useShift: Boolean = true): IChunk
    fun loadChunk(pos: Vector3Int, useShift: Boolean = true): IChunk

    fun unloadChunk(chunk: IChunk): Boolean
    fun unloadChunk(x: Int, z: Int, useShift: Boolean = true): Boolean
    fun unloadChunk(pos: Vector2Int, useShift: Boolean = true): Boolean
    fun unloadChunk(x: Int, y: Int, z: Int, useShift: Boolean = true): Boolean
    fun unloadChunk(pos: Vector3Int, useShift: Boolean = true): Boolean

    fun registerChunkLoader(loader: IChunkLoader)
    fun unregisterChunkLoader(loader: IChunkLoader)
    fun unregisterChunkLoader(id: Long)

    fun getChunkRadius(x: Int, z: Int, loader: IChunkLoader): Array<IChunk>
}