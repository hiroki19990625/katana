package jp.katana.nbt.tag

import jp.katana.io.NBTStream

class LongTag(override var name: String, override var value: Long) : ValueTag<Long>() {
    override val type: Byte = INamedTag.LONG

    override fun write(stream: NBTStream) {
        stream.writeLong(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readLong()
    }

    override fun equals(other: Any?): Boolean {
        return other is LongTag && other.value == value || other is Long && other == value
    }
}