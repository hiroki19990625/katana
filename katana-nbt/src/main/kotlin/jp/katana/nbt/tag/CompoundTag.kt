package jp.katana.nbt.tag

import jp.katana.debug.appendIndent
import jp.katana.nbt.io.NBTStream

class CompoundTag(override var name: String) : INamedTag {
    override val type: Byte = INamedTag.COMPOUND
    private val map = mutableMapOf<String, INamedTag>()

    fun putTag(tag: INamedTag): Boolean {
        val name = tag.name
        if (name.isNotEmpty()) {
            map[tag.name] = tag
            return true
        }

        return false
    }

    fun putBoolean(name: String, value: Boolean): Boolean {
        return putTag(ByteTag(name, if (value) 1 else 0))
    }

    fun putByte(name: String, value: Byte): Boolean {
        return putTag(ByteTag(name, value))
    }

    fun putShort(name: String, value: Short): Boolean {
        return putTag(ShortTag(name, value))
    }

    fun putInt(name: String, value: Int): Boolean {
        return putTag(IntTag(name, value))
    }

    fun putLong(name: String, value: Long): Boolean {
        return putTag(LongTag(name, value))
    }

    fun putFloat(name: String, value: Float): Boolean {
        return putTag(FloatTag(name, value))
    }

    fun putDouble(name: String, value: Double): Boolean {
        return putTag(DoubleTag(name, value))
    }

    fun putString(name: String, value: String): Boolean {
        return putTag(StringTag(name, value))
    }

    fun putByteArray(name: String, value: Array<Byte>): Boolean {
        return putTag(ByteArrayTag(name, value))
    }

    fun putByteArray(name: String, value: ByteArray): Boolean {
        return putByteArray(name, value.toTypedArray())
    }

    fun putIntArray(name: String, value: Array<Int>): Boolean {
        return putTag(IntArrayTag(name, value))
    }

    fun putIntArray(name: String, value: IntArray): Boolean {
        return putIntArray(name, value.toTypedArray())
    }

    fun putLongArray(name: String, value: Array<Long>): Boolean {
        return putTag(LongArrayTag(name, value))
    }

    fun putLongArray(name: String, value: LongArray): Boolean {
        return putLongArray(name, value.toTypedArray())
    }

    fun getBoolean(name: String): Boolean {
        return getByteTag(name).value == 1.toByte()
    }

    fun getByteTag(name: String): ByteTag {
        return map[name] as ByteTag
    }

    fun getByte(name: String): Byte {
        return getByteTag(name).value
    }

    fun getShortTag(name: String): ShortTag {
        return map[name] as ShortTag
    }

    fun getShort(name: String): Short {
        return getShortTag(name).value
    }

    fun getIntTag(name: String): IntTag {
        return map[name] as IntTag
    }

    fun getInt(name: String): Int {
        return getIntTag(name).value
    }

    fun getLongTag(name: String): LongTag {
        return map[name] as LongTag
    }

    fun getLong(name: String): Long {
        return getLongTag(name).value
    }

    fun getFloatTag(name: String): FloatTag {
        return map[name] as FloatTag
    }

    fun getFloat(): Float {
        return getFloatTag(name).value
    }

    fun getDoubleTag(name: String): DoubleTag {
        return map[name] as DoubleTag
    }

    fun getDouble(name: String): Double {
        return getDoubleTag(name).value
    }

    fun getByteArrayTag(name: String): ByteArrayTag {
        return map[name] as ByteArrayTag
    }

    fun getByteArray(name: String): Array<Byte> {
        return getByteArrayTag(name).value
    }

    fun getStringTag(name: String): StringTag {
        return map[name] as StringTag
    }

    fun getString(name: String): String {
        return getStringTag(name).value
    }

    fun getListTag(name: String): ListTag {
        return map[name] as ListTag
    }

    fun getCompoundTag(name: String): CompoundTag {
        return map[name] as CompoundTag
    }

    fun getIntArrayTag(name: String): IntArrayTag {
        return map[name] as IntArrayTag
    }

    fun getIntArray(name: String): Array<Int> {
        return getIntArrayTag(name).value
    }

    fun getLongArrayTag(name: String): LongArrayTag {
        return map[name] as LongArrayTag
    }

    fun getLongArray(name: String): Array<Long> {
        return getLongArrayTag(name).value
    }

    operator fun get(name: String): INamedTag {
        return map[name]!!
    }

    operator fun set(name: String, tag: INamedTag) {
        map[name] = tag
    }

    fun getAllTag(): Map<String, INamedTag> {
        return map.toMap()
    }

    fun removeTag(name: String) {
        map.remove(name)
    }

    fun removeAllTag() {
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
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent(indent)
        builder.append("${this.javaClass.simpleName} : $name = {" + "\n")
        for (tag in map.values) {
            tag.print(builder, indent + 1)
        }
        builder.appendIndent(indent)
        builder.append("}\n")
    }
}