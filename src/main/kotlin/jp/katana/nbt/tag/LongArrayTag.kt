package jp.katana.nbt.tag

import jp.katana.nbt.io.NBTStream

class LongArrayTag(override var name: String, override var value: Array<Long>) : ArrayTag<Long>() {
    override val type: Byte = INamedTag.LONG_ARRAY

    override fun write(stream: NBTStream) {
        stream.writeInt(value.size)
        for (v in value) {
            stream.writeLong(v)
        }
    }

    override fun read(stream: NBTStream) {
        val size = stream.readInt()
        val array = LongArray(size)
        for (i in 0 until size) {
            array[i] = stream.readLong()
        }

        value = array.toTypedArray()
    }
}