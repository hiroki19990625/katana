package jp.katana.nbt.tag

import jp.katana.nbt.io.NBTStream

class ByteTag(override var name: String, override var value: Byte) : ValueTag<Byte>() {
    override val type: Byte = INamedTag.BYTE

    override fun write(stream: NBTStream) {
        stream.writeByte(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readByte()
    }

    override fun equals(other: Any?): Boolean {
        return other is ByteTag && other.value == value || other is Byte && other == value
    }
}