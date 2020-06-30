package jp.katana.nbt.tag

import jp.katana.io.NBTStream

class StringTag(override var name: String, override var value: String) : ValueTag<String>() {
    override val type: Byte = INamedTag.STRING

    override fun write(stream: NBTStream) {
        stream.writeString(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readString()
    }

    override fun equals(other: Any?): Boolean {
        return other is StringTag && other.value == value || other is String && other == value
    }
}