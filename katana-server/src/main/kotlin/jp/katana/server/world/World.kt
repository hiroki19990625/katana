package jp.katana.server.world

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.world.IWorld
import jp.katana.core.world.WorldType
import jp.katana.core.world.chunk.IChunk
import jp.katana.core.world.chunk.IChunkLoader
import jp.katana.core.world.gamerule.IGameRules
import jp.katana.math.Vector2Int
import jp.katana.math.Vector3Int
import jp.katana.server.block.BlockDefinitions
import jp.katana.server.network.packet.mcpe.NetworkChunkPublisherUpdatePacket
import jp.katana.server.world.chunk.Chunk
import jp.katana.server.world.gamerule.GameRules
import java.io.File


class World(
    override val name: String,
    override val server: IServer,
    override val worldType: WorldType
) : IWorld {
    private val chunks: MutableMap<Vector2Int, IChunk> = mutableMapOf()
    private val chunkLoaders: MutableMap<Long, IChunkLoader> = mutableMapOf()

    constructor(name: String, server: IServer) : this(name, server, WorldType.Default)

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
        // TODO: Load Chunk
        return if (useShift) {
            val c = Chunk(this, Vector2Int(x shr 4, z shr 4))
            chunks[c.pos] = c
            c
        } else {
            val c = Chunk(this, Vector2Int(x, z))
            chunks[c.pos] = c
            c
        }
    }

    override fun loadChunk(pos: Vector2Int, useShift: Boolean): IChunk {
        return loadChunk(pos.x, pos.y, useShift)
    }

    override fun loadChunk(x: Int, y: Int, z: Int, useShift: Boolean): IChunk {
        return loadChunk(x, z, useShift)
    }

    override fun loadChunk(pos: Vector3Int, useShift: Boolean): IChunk {
        return loadChunk(pos.x, pos.z, useShift)
    }

    override fun unloadChunk(chunk: IChunk): Boolean {
        return unloadChunk(chunk.pos.x, chunk.pos.y, false)
    }

    override fun unloadChunk(x: Int, z: Int, useShift: Boolean): Boolean {
        // TODO: Save Chunk
        if (useShift) {
            chunks.remove(Vector2Int(x shr 4, z shr 4))
        } else {
            chunks.remove(Vector2Int(x, z))
        }

        return true
    }

    override fun unloadChunk(pos: Vector2Int, useShift: Boolean): Boolean {
        return unloadChunk(pos.x, pos.y, useShift)
    }

    override fun unloadChunk(x: Int, y: Int, z: Int, useShift: Boolean): Boolean {
        return unloadChunk(x, z, useShift)
    }

    override fun unloadChunk(pos: Vector3Int, useShift: Boolean): Boolean {
        return unloadChunk(pos.x, pos.z, useShift)
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

    override fun getChunkRadius(loader: IChunkLoader): Sequence<IChunk> {
        val newOrders = mutableMapOf<Vector2Int, Double>()

        val radius = loader.getRadius()
        val radiusSquared = radius.toDouble().pow(2.0)
        val center = loader.getChunkPosition()

        for (xx in -radius..radius) {
            for (zz in -radius..radius) {
                val distance = xx * xx + zz * zz
                if (distance > radiusSquared) {
                    continue
                }

                val chunkX = (xx + center.x)
                val chunkZ = (zz + center.y)
                val index = Vector2Int(chunkX, chunkZ)
                newOrders[index] = distance.toDouble()
            }
        }

        for (key in chunks.keys) {
            if (!newOrders.containsKey(key)) {
                unloadChunk(key, false)
            }

            loader.getLoadedChunksMap().remove(key)
        }

        return sequence {
            for (pair in newOrders.toList().sortedBy { e -> e.second }) {
                if (loader.getLoadedChunksMap().containsKey(pair.first)) continue

                val chunk = getChunk(pair.first, false)
                if (!chunks.containsKey(pair.first))
                    chunks[pair.first] = chunk

                loader.getLoadedChunksMap()[pair.first] = pair.second
                yield(chunk)
            }
        }
    }

    override fun sendChunks(player: IActorPlayer): Boolean {
        val chunks = getChunkRadius(player)
        for (chunk in chunks) {
            var i = 0
            for (x in 1..5) {
                for (y in 1..5) {
                    for (z in 1..5) {
                        for (p in 0..1) {
                            chunk.columns[p].setRuntimeId(
                                Vector3Int(x, y, z),
                                BlockDefinitions.fromId(i).runtimeId
                            )
                            chunk.columns[p].setLiquidRuntimeId(
                                Vector3Int(x, y, z),
                                BlockDefinitions.fromId(8).runtimeId
                            )
                        }
                        i++
                    }
                }
            }
            player.sendPacket(chunk.getChunkPacket())
        }

        val publisherUpdatePacket = NetworkChunkPublisherUpdatePacket()
        publisherUpdatePacket.position = player.position.toVector3Int()
        publisherUpdatePacket.radius = (player.getRadius() + 1) shl 4
        player.sendPacket(publisherUpdatePacket)

        return true
    }

    private fun getChunk(x: Int, z: Int): IChunk? {
        return chunks[Vector2Int(x, z)]
    }

    private fun getShiftChunk(x: Int, z: Int): IChunk? {
        return chunks[Vector2Int(x shr 4, z shr 4)]
    }
}