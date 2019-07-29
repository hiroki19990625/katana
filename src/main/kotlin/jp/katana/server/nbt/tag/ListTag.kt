package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

class ListTag(override var name: String, listType: Byte = INamedTag.BYTE) : INamedTag {
    override val type: Byte = INamedTag.LIST
    var listType: Byte = listType
        private set
    val list = mutableListOf<INamedTag>()

    fun add(tag: INamedTag): Boolean {
        return if (tag.type == listType)
            list.add(tag)
        else
            false
    }

    fun <T : INamedTag> get(index: Int): T {
        return list[index] as T
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