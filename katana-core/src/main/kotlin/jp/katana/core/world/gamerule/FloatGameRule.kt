package jp.katana.core.world.gamerule

import jp.katana.utils.BinaryStream

class FloatGameRule(name: String, override var value: Float) : IGameRule<Float> {
    override val type: Byte = IGameRule.FLOAT
    override var name: String = name
        private set

    override fun write(stream: BinaryStream) {
        stream.writeVarString(name.toLowerCase())
        stream.writeByte(type)
        stream.writeFloatLE(value)
    }

    override fun read(stream: BinaryStream) {
        value = stream.readFloatLE()
    }
}