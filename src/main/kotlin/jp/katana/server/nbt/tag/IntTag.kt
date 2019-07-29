package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

class IntTag(override var name: String, override var value: Int) : ValueTag<Int>() {
    override val type: Byte = INamedTag.INT

    override fun write(stream: NBTStream) {
        stream.writeInt(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readInt()
    }
}