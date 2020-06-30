package jp.katana.nbt.tag

import jp.katana.io.NBTStream

class ShortTag(override var name: String, override var value: Short) : ValueTag<Short>() {
    override val type: Byte = INamedTag.SHORT

    override fun write(stream: NBTStream) {
        stream.writeShort(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readShort()
    }

    override fun equals(other: Any?): Boolean {
        return other is ShortTag && other.value == value || other is Short && other == value
    }
}