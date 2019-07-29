package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

class FloatTag(override var name: String, override var value: Float) : ValueTag<Float>() {
    override val type: Byte = INamedTag.FLOAT

    override fun write(stream: NBTStream) {
        stream.writeFloat(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readFloat()
    }
}
