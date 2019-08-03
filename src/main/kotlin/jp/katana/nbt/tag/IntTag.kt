package jp.katana.nbt.tag

import jp.katana.nbt.io.NBTStream

class IntTag(override var name: String, override var value: Int) : ValueTag<Int>() {
    override val type: Byte = INamedTag.INT

    override fun write(stream: NBTStream) {
        stream.writeInt(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readInt()
    }

    override fun equals(other: Any?): Boolean {
        return other is IntTag && other.value == value || other is Int && other == value
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + value
        result = 31 * result + type
        return result
    }
}