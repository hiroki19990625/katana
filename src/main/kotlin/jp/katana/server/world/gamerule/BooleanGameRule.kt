package jp.katana.server.world.gamerule

import jp.katana.core.world.gamerule.IGameRule
import jp.katana.server.utils.BinaryStream

class BooleanGameRule(name: String, override var value: Boolean) : IGameRule<Boolean> {
    override val type: Byte = IGameRule.BOOLEAN
    override var name: String = name
        private set

    override fun write(stream: BinaryStream) {
        stream.writeVarString(name.toLowerCase())
        stream.writeByte(type.toInt())
        stream.writeBoolean(value)
    }

    override fun read(stream: BinaryStream) {
        value = stream.readBoolean()
    }
}