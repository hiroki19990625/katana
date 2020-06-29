package jp.katana.core.actor.data

import jp.katana.utils.BinaryStream

interface IActorData<T> {
    companion object {
        const val BYTE = 0
        const val SHORT = 1
        const val INT = 2
        const val FLOAT = 3
        const val STRING = 4
        const val NBT = 5
        const val POS = 6
        const val LONG = 7
        const val VECTOR3F = 8
    }

    val type: Int
    var value: T

    fun read(stream: BinaryStream)
    fun write(stream: BinaryStream)
}