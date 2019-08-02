package jp.katana.core.world.gamerule

import jp.katana.server.utils.BinaryStream

interface IGameRule<T> {
    companion object {
        const val BOOLEAN: Byte = 1
        const val INT: Byte = 2
        const val FLOAT: Byte = 3
    }

    val name: String
    val type: Byte
    var value: T

    fun write(stream: BinaryStream)
    fun read(stream: BinaryStream)
}
