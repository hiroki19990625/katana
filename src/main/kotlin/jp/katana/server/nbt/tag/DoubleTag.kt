package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

class DoubleTag(override var name: String, override var value: Double) : ValueTag<Double>() {
    override val type: Byte = INamedTag.DOUBLE

    override fun write(stream: NBTStream) {
        stream.writeDouble(value)
    }

    override fun read(stream: NBTStream) {
        value = stream.readDouble()
    }

    override fun equals(other: Any?): Boolean {
        return other is DoubleTag && other.value == value || other is Double && other == value
    }
}