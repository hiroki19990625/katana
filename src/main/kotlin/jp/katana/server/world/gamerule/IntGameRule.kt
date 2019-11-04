package jp.katana.server.world.gamerule

import jp.katana.core.world.gamerule.IGameRule
import jp.katana.utils.BinaryStream

class IntGameRule(name: String, override var value: Int) : IGameRule<Int> {
    override val type: Byte = IGameRule.INT
    override var name: String = name
        private set

    override fun write(stream: BinaryStream) {
        stream.writeVarString(name.toLowerCase())
        stream.writeByte(type)
        stream.writeVarInt(value)
    }

    override fun read(stream: BinaryStream) {
        value = stream.readVarInt()
    }
}