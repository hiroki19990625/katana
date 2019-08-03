package jp.katana.nbt.tag

import jp.katana.nbt.io.NBTStream

class CompoundTag(override var name: String) : INamedTag {
    override val type: Byte = INamedTag.COMPOUND
    private val map = mutableMapOf<String, INamedTag>()

    fun put(tag: INamedTag): Boolean {
        val name = tag.name
        if (name.isNotEmpty()) {
            map[tag.name] = tag
            return true
        }

        return false
    }

    fun getByte(name: String): ByteTag {
        return map[name] as ByteTag
    }

    fun getShort(name: String): ShortTag {
        return map[name] as ShortTag
    }

    fun getInt(name: String): IntTag {
        return map[name] as IntTag
    }

    fun getLong(name: String): LongTag {
        return map[name] as LongTag
    }

    fun getFloat(name: String): FloatTag {
        return map[name] as FloatTag
    }

    fun getDouble(name: String): DoubleTag {
        return map[name] as DoubleTag
    }

    fun getByteArray(name: String): ByteArrayTag {
        return map[name] as ByteArrayTag
    }

    fun getString(name: String): StringTag {
        return map[name] as StringTag
    }

    fun getList(name: String): ListTag {
        return map[name] as ListTag
    }

    fun getCompound(name: String): CompoundTag {
        return map[name] as CompoundTag
    }

    fun getIntArray(name: String): IntArrayTag {
        return map[name] as IntArrayTag
    }

    fun getLongArray(name: String): LongArrayTag {
        return map[name] as LongArrayTag
    }

    operator fun get(name: String): INamedTag {
        return map[name]!!
    }

    fun getAll(): Map<String, INamedTag> {
        return map.toMap()
    }

    fun remove(name: String) {
        map.remove(name)
    }

    fun removeAll() {
        map.clear()
    }

    fun size(): Int {
        return map.size
    }

    fun contains(name: String): Boolean {
        return map.contains(name)
    }

    override fun write(stream: NBTStream) {
        for (entry in map.values) {
            stream.writeByte(entry.type)
            stream.writeString(entry.name)
            entry.write(stream)
        }

        stream.writeByte(INamedTag.END)
    }

    override fun read(stream: NBTStream) {
        var type = stream.readByte()
        while (type != INamedTag.END) {
            val tag: INamedTag = INamedTag.getTag(type, stream.readString())
            tag.read(stream)
            map[tag.name] = tag

            type = stream.readByte()
        }
    }

    override fun toString(): String {
        var str = "${this.javaClass.simpleName} : $name = {" + "\n"
        for (tag in map.values) {
            str += tag.toString() + "\n"
        }
        str += "}"

        return str
    }
}