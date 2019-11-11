package jp.katana.server.world

import jp.katana.core.world.IWorld
import jp.katana.core.world.WorldType
import jp.katana.core.world.chunk.IChunk
import jp.katana.core.world.chunk.IChunkLoader
import jp.katana.core.world.gamerule.IGameRules
import jp.katana.math.Vector2Int
import jp.katana.math.Vector3Int
import jp.katana.server.world.gamerule.GameRules
import java.io.File

class World(override val name: String, override val worldType: WorldType) : IWorld {
    private val chunks: MutableMap<Vector2Int, IChunk> = mutableMapOf()
    private val chunkLoaders: MutableMap<Long, IChunkLoader> = mutableMapOf()

    constructor(name: String) : this(name, WorldType.Default)

    override val gameRules: IGameRules
        get() = GameRules()

    override fun loadData() {

    }

    override fun loadData(file: File) {

    }

    override fun save() {

    }

    override fun getChunk(x: Int, z: Int, useShift: Boolean): IChunk {
        return (if (useShift)
            getShiftChunk(x, z)
        else
            getChunk(x, z)) ?: loadChunk(x, z, useShift)
    }

    override fun getChunk(pos: Vector2Int, useShift: Boolean): IChunk {
        return getChunk(pos.x, pos.y, useShift)
    }

    override fun getChunk(x: Int, y: Int, z: Int, useShift: Boolean): IChunk {
        return getChunk(x, z, useShift)
    }

    override fun getChunk(pos: Vector3Int, useShift: Boolean): IChunk {
        return getChunk(pos.x, pos.z, useShift)
    }

    override fun loadChunk(x: Int, z: Int, useShift: Boolean): IChunk {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadChunk(pos: Vector2Int, useShift: Boolean): IChunk {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadChunk(x: Int, y: Int, z: Int, useShift: Boolean): IChunk {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadChunk(pos: Vector3Int, useShift: Boolean): IChunk {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unloadChunk(chunk: IChunk): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unloadChunk(x: Int, z: Int, useShift: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unloadChunk(pos: Vector2Int, useShift: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unloadChunk(x: Int, y: Int, z: Int, useShift: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unloadChunk(pos: Vector3Int, useShift: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerChunkLoader(loader: IChunkLoader) {
        chunkLoaders[loader.getLoaderId()] = loader
    }

    override fun unregisterChunkLoader(loader: IChunkLoader) {
        unregisterChunkLoader(loader.getLoaderId())
    }

    override fun unregisterChunkLoader(id: Long) {
        chunkLoaders.remove(id)
    }

    override fun getChunkRadius(x: Int, z: Int, loader: IChunkLoader): Array<IChunk> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getChunk(x: Int, z: Int): IChunk? {
        return chunks[Vector2Int(x, z)]
    }

    private fun getShiftChunk(x: Int, z: Int): IChunk? {
        return chunks[Vector2Int(x shr 4, z shr 4)]
    }
}