package jp.katana.core.world.chunk

import jp.katana.math.Vector3Int
import jp.katana.nbt.tag.CompoundTag

interface ISubChunk {
    companion object {
        const val BLOCK_SIZE = 4096

        const val VERSION = 8
        const val LAYER = 2

        const val MAX = 16
        const val MAX2 = 256
    }

    val y: Int

    val blocks: IntArray
    val liquids: IntArray

    fun getRuntimeId(pos: Vector3Int): Int
    fun setRuntimeId(pos: Vector3Int, runtime: Int)

    fun getLiquidRuntimeId(pos: Vector3Int): Int
    fun setLiquidRuntimeId(pos: Vector3Int, runtime: Int)

    fun isEmptyBlocks(): Boolean {
        return blocks.none { b -> b == 0 }
    }

    fun isEmptyLiquids(): Boolean {
        return liquids.none { b -> b == 0 }
    }

    fun isEmpty(): Boolean {
        return isEmptyBlocks() && isEmptyLiquids()
    }

    fun networkSerialize(): ByteArray
    fun networkDeserialize(buf: ByteArray)

    fun nbtSerialize(): CompoundTag
    fun nbtDeserialize(tag: CompoundTag)
}