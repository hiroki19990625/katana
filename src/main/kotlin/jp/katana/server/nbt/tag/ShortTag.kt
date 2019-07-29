package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

class ShortTag(override var name: String, override var value: Short) : ValueTag<Short>() {
    override val type: Byte = INamedTag.SHORT

    override fun write(stream: NBTStream) {
        stream.writeShort(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readShort()
    }
}