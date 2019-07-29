package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

class ByteArrayTag(override var name: String, override var value: Array<Byte>) : ArrayTag<Byte>() {
    override val type: Byte = INamedTag.BYTE_ARRAY

    override fun write(stream: NBTStream) {
        stream.writeInt(value.size)
        for (v in value) {
            stream.writeByte(v)
        }
    }

    override fun read(stream: NBTStream) {
        val size = stream.readInt()
        val array = ByteArray(size)
        for (i in 0 until size) {
            array[i] = stream.readByte()
        }

        value = array.toTypedArray()
    }
}