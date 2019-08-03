package jp.katana.nbt.tag

import jp.katana.nbt.io.NBTStream

class IntArrayTag(override var name: String, override var value: Array<Int>) : ArrayTag<Int>() {
    override val type: Byte = INamedTag.INT_ARRAY

    override fun write(stream: NBTStream) {
        stream.writeInt(value.size)
        for (v in value) {
            stream.writeInt(v)
        }
    }

    override fun read(stream: NBTStream) {
        val size = stream.readInt()
        val array = IntArray(size)
        for (i in 0 until size) {
            array[i] = stream.readInt()
        }

        value = array.toTypedArray()
    }
}