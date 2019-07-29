package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

interface INamedTag {
    companion object {
        const val END: Byte = 0
        const val BYTE: Byte = 1
        const val SHORT: Byte = 2
        const val INT: Byte = 3
        const val LONG: Byte = 4
        const val FLOAT: Byte = 5
        const val DOUBLE: Byte = 6
        const val BYTE_ARRAY: Byte = 7
        const val STRING: Byte = 8
        const val LIST: Byte = 9
        const val COMPOUND: Byte = 10
        const val INT_ARRAY: Byte = 11
        const val LONG_ARRAY: Byte = 12

        fun getTag(type: Byte, name: String = ""): INamedTag {
            return when (type) {
                END -> EndTag()
                BYTE -> ByteTag(name, 0)
                SHORT -> ShortTag(name, 0)
                INT -> IntTag(name, 0)
                LONG -> LongTag(name, 0)
                FLOAT -> FloatTag(name, 0f)
                DOUBLE -> DoubleTag(name, 0.0)
                BYTE_ARRAY -> ByteArrayTag(name, ByteArray(0).toTypedArray())
                STRING -> StringTag(name, "")
                LIST -> ListTag(name)
                COMPOUND -> CompoundTag(name)
                INT_ARRAY -> IntArrayTag(name, IntArray(0).toTypedArray())
                LONG_ARRAY -> LongArrayTag(name, LongArray(0).toTypedArray())
                else -> EndTag()
            }
        }
    }

    var name: String
    val type: Byte

    fun write(stream: NBTStream)
    fun read(stream: NBTStream)
}