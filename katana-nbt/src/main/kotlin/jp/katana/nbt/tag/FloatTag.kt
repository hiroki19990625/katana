package jp.katana.nbt.tag

import jp.katana.io.NBTStream

class FloatTag(override var name: String, override var value: Float) : ValueTag<Float>() {
    override val type: Byte = INamedTag.FLOAT

    override fun write(stream: NBTStream) {
        stream.writeFloat(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readFloat()
    }

    override fun equals(other: Any?): Boolean {
        return other is FloatTag && other.value == value || other is Float && other == value
    }
}
