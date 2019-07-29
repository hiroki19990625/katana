package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

class StringTag(override var name: String, override var value: String) : ValueTag<String>() {
    override val type: Byte = INamedTag.STRING

    override fun write(stream: NBTStream) {
        stream.writeString(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readString()
    }
}