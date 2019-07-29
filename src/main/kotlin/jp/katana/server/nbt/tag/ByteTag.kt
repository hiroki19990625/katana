package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

class ByteTag(override var name: String, override var value: Byte) : ValueTag<Byte>() {
    override val type: Byte = INamedTag.BYTE

    override fun write(stream: NBTStream) {
        stream.writeByte(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readByte()
    }
}