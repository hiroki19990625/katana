package jp.katana.server.factory

import jp.katana.server.nbt.tag.*

class NBTTagFactory : SimpleFactory<Byte, (String) -> INamedTag>() {
    init {
        this += { EndTag() }
        this += { name -> ByteTag(name, 0) }
        this += { name -> ShortTag(name, 0) }
        this += { name -> IntTag(name, 0) }
        this += { name -> LongTag(name, 0) }
        this += { name -> FloatTag(name, 0f) }
        this += { name -> DoubleTag(name, 0.0) }
        this += { name -> ByteArrayTag(name, emptyArray()) }
        this += { name -> StringTag(name, "") }
        this += { name -> ListTag(name) }
        this += { name -> CompoundTag(name) }
        this += { name -> IntArrayTag(name, emptyArray()) }
        this += { name -> LongArrayTag(name, emptyArray()) }
    }

    override fun plusAssign(value: (String) -> INamedTag) {
        val v = value("")
        if (!map.containsKey(v.type))
            map[v.type] = value
    }

    override fun get(name: Byte): ((String) -> INamedTag)? {
        if (map.containsKey(name)) {
            return map[name]
        }

        return null
    }
}