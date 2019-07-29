package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

class LongTag(override var name: String, override var value: Long) : ValueTag<Long>() {
    override val type: Byte = INamedTag.LONG

    override fun write(stream: NBTStream) {
        stream.writeLong(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readLong()
    }
}