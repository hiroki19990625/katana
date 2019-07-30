package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

class ListTag(override var name: String, listType: Byte = INamedTag.BYTE) : INamedTag {
    override val type: Byte = INamedTag.LIST
    var listType: Byte = listType
        private set
    private val list = mutableListOf<INamedTag>()

    fun add(tag: INamedTag): Boolean {
        return if (tag.type == listType)
            list.add(tag)
        else
            false
    }

    fun getByte(index: Int): ByteTag {
        return list[index] as ByteTag
    }

    fun getShort(index: Int): ShortTag {
        return list[index] as ShortTag
    }

    fun getInt(index: Int): IntTag {
        return list[index] as IntTag
    }

    fun getLong(index: Int): LongTag {
        return list[index] as LongTag
    }

    fun getFloat(index: Int): FloatTag {
        return list[index] as FloatTag
    }

    fun getDouble(index: Int): DoubleTag {
        return list[index] as DoubleTag
    }

    fun getByteArray(index: Int): ByteArrayTag {
        return list[index] as ByteArrayTag
    }

    fun getString(index: Int): StringTag {
        return list[index] as StringTag
    }

    fun getList(index: Int): ListTag {
        return list[index] as ListTag
    }

    fun getCompound(index: Int): CompoundTag {
        return list[index] as CompoundTag
    }

    fun getIntArray(index: Int): IntArrayTag {
        return list[index] as IntArrayTag
    }

    fun getLongArray(index: Int): LongArrayTag {
        return list[index] as LongArrayTag
    }

    operator fun get(index: Int): INamedTag {
        return list[index]
    }

    fun getAll(): Array<INamedTag> {
        return list.toTypedArray()
    }

    fun remove(tag: INamedTag): Boolean {
        return list.remove(tag)
    }

    fun size(): Int {
        return list.size
    }

    fun contains(tag: INamedTag): Boolean {
        return list.contains(tag)
    }

    fun removeAll() {
        list.clear()
    }

    override fun write(stream: NBTStream) {
        stream.writeByte(listType)
        stream.writeInt(list.size)
        list.forEach { t -> t.write(stream) }
    }

    override fun read(stream: NBTStream) {
        listType = stream.readByte()
        val size = stream.readInt()
        for (i in 0 until size) {
            val t: INamedTag = INamedTag.getTag(listType)
            t.read(stream)
            list.add(t)
        }
    }

    override fun toString(): String {
        var str = "${this.javaClass.simpleName} : $name = [" + "\n"
        for (tag in list) {
            str += tag.toString() + "\n"
        }
        str += "]"

        return str
    }
}