package jp.katana.server.nbt.tag

import jp.katana.server.nbt.io.NBTStream

class CompoundTag(override var name: String) : INamedTag {
    override val type: Byte = INamedTag.COMPOUND
    val map = mutableMapOf<String, INamedTag>()

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