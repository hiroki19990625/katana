package jp.katana.server.world.chunk

import jp.katana.core.world.chunk.ISubChunk
import jp.katana.math.Vector3Int
import jp.katana.nbt.tag.CompoundTag
import jp.katana.utils.BinaryStream
import kotlin.math.ceil
import kotlin.math.log

class SubChunk(override val y: Int) : ISubChunk {
    override val blocks: IntArray = IntArray(ISubChunk.BLOCK_SIZE)
    override val liquids: IntArray = IntArray(ISubChunk.BLOCK_SIZE)

    override fun getRuntimeId(pos: Vector3Int): Int {
        val index = getArrayIndex(pos.x, pos.y, pos.z)
        return blocks[index]
    }

    override fun setRuntimeId(pos: Vector3Int, runtime: Int) {
        val index = getArrayIndex(pos.x, pos.y, pos.z)
        blocks[index] = runtime
    }

    override fun getLiquidRuntimeId(pos: Vector3Int): Int {
        val index = getArrayIndex(pos.x, pos.y, pos.z)
        return liquids[index]
    }

    override fun setLiquidRuntimeId(pos: Vector3Int, runtime: Int) {
        val index = getArrayIndex(pos.x, pos.y, pos.z)
        liquids[index] = runtime
    }

    override fun networkSerialize(): ByteArray {
        val stream = BinaryStream()
        stream.writeByte(ISubChunk.VERSION.toByte())

        stream.writeByte(ISubChunk.LAYER.toByte())
        writePalette(stream, blocks)
        //writePalette(stream, liquids)

        val buf = stream.array()
        stream.close()
        return buf
    }

    override fun networkDeserialize(buf: ByteArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun nbtSerialize(): CompoundTag {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun nbtDeserialize(tag: CompoundTag) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getArrayIndex(x: Int, y: Int, z: Int): Int {
        return x * ISubChunk.MAX2 + z * ISubChunk.MAX + y
    }

    private fun writePalette(stream: BinaryStream, data: IntArray) {
        val palettes = mutableListOf<Int>()
        for (d in data) {
            if (!palettes.contains(d))
                palettes.add(d)
        }

        var bitsPerBlock = ceil(log(palettes.size.toDouble(), 2.0)).toInt()
        when (bitsPerBlock) {
            0 -> bitsPerBlock = 1
            in 7..8 -> bitsPerBlock = 8
            in 9..16 -> bitsPerBlock = 16
        }

        /*stream.writeByte((bitsPerBlock shl 1 or 1).toByte())

        val blocksPerWord = floor(32f / bitsPerBlock).toInt()
        val wordsPerChunk = ceil(4096f / blocksPerWord).toInt()

        var point = 0
        for (w in 0 until wordsPerChunk) {
            var word = 0
            for (block in 0 until blocksPerWord) {
                if (point >= 4096)
                    continue
                word = word or palettes.indexOf(data[point++]) shl (bitsPerBlock * block)
            }

            stream.writeIntLE(word)
        }*/
        stream.writeByte(8 shl 1 or 1)
        for (d in data) {
            stream.writeByte(palettes.indexOf(d).toByte())
        }

        stream.writeUnsignedVarInt(palettes.size)
        for (p in palettes)
            stream.writeVarInt(p)
    }
}