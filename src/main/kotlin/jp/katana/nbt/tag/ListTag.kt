package jp.katana.nbt.tag

import jp.katana.nbt.io.NBTStream

class ListTag(override var name: String, listType: Byte = INamedTag.BYTE) : INamedTag {
    override val type: Byte = INamedTag.LIST
    var listType: Byte = listType
        private set
    private val list = mutableListOf<INamedTag>()

    fun addTag(tag: INamedTag): Boolean {
        return if (tag.type == listType)
            list.add(tag)
        else
            false
    }

    fun addBoolean(value: Boolean): Boolean {
        return if (listType == INamedTag.BYTE)
            list.add(ByteTag("", if (value) 1 else 0))
        else
            false
    }

    fun addByte(value: Byte): Boolean {
        return if (listType == INamedTag.BYTE)
            list.add(ByteTag("", value))
        else
            false
    }

    fun addShort(value: Short): Boolean {
        return if (listType == INamedTag.SHORT)
            list.add(ShortTag("", value))
        else
            false
    }

    fun addInt(value: Int): Boolean {
        return if (listType == INamedTag.INT)
            list.add(IntTag("", value))
        else
            false
    }

    fun addLong(value: Long): Boolean {
        return if (listType == INamedTag.LONG)
            list.add(LongTag("", value))
        else
            false
    }

    fun addFloat(value: Float): Boolean {
        return if (listType == INamedTag.FLOAT)
            list.add(FloatTag("", value))
        else
            false
    }

    fun addDouble(value: Double): Boolean {
        return if (listType == INamedTag.DOUBLE)
            list.add(DoubleTag("", value))
        else
            false
    }

    fun addString(value: String): Boolean {
        return if (listType == INamedTag.STRING)
            list.add(StringTag("", value))
        else
            false
    }

    fun addByteArray(value: Array<Byte>): Boolean {
        return if (listType == INamedTag.BYTE_ARRAY)
            list.add(ByteArrayTag("", value))
        else
            false
    }

    fun addByteArray(value: ByteArray): Boolean {
        return if (listType == INamedTag.BYTE_ARRAY)
            list.add(ByteArrayTag("", value.toTypedArray()))
        else
            false
    }

    fun addIntArray(value: Array<Int>): Boolean {
        return if (listType == INamedTag.INT_ARRAY)
            list.add(IntArrayTag("", value))
        else
            false
    }

    fun addIntArray(value: IntArray): Boolean {
        return if (listType == INamedTag.INT_ARRAY)
            list.add(IntArrayTag("", value.toTypedArray()))
        else
            false
    }

    fun addLongArray(value: Array<Long>): Boolean {
        return if (listType == INamedTag.LONG_ARRAY)
            list.add(LongArrayTag("", value))
        else
            false
    }

    fun addLongArray(value: LongArray): Boolean {
        return if (listType == INamedTag.LONG_ARRAY)
            list.add(LongArrayTag("", value.toTypedArray()))
        else
            false
    }

    fun getBoolean(index: Int): Boolean {
        return getByte(index) == 1.toByte()
    }

    fun getByteTag(index: Int): ByteTag {
        return list[index] as ByteTag
    }

    fun getByte(index: Int): Byte {
        return getByteTag(index).value
    }

    fun getShortTag(index: Int): ShortTag {
        return list[index] as ShortTag
    }

    fun getShort(index: Int): Short {
        return getShortTag(index).value
    }

    fun getIntTag(index: Int): IntTag {
        return list[index] as IntTag
    }

    fun getInt(index: Int): Int {
        return getIntTag(index).value
    }

    fun getLongTag(index: Int): LongTag {
        return list[index] as LongTag
    }

    fun getLong(index: Int): Long {
        return getLongTag(index).value
    }

    fun getFloatTag(index: Int): FloatTag {
        return list[index] as FloatTag
    }

    fun getFloat(index: Int): Float {
        return getFloatTag(index).value
    }

    fun getDoubleTag(index: Int): DoubleTag {
        return list[index] as DoubleTag
    }

    fun getDouble(index: Int): Double {
        return getDoubleTag(index).value
    }

    fun getByteArrayTag(index: Int): ByteArrayTag {
        return list[index] as ByteArrayTag
    }

    fun getByteArray(index: Int): Array<Byte> {
        return getByteArrayTag(index).value
    }

    fun getStringTag(index: Int): StringTag {
        return list[index] as StringTag
    }

    fun getString(index: Int): String {
        return getStringTag(index).value
    }

    fun getListTag(index: Int): ListTag {
        return list[index] as ListTag
    }

    fun getCompoundTag(index: Int): CompoundTag {
        return list[index] as CompoundTag
    }

    fun getIntArrayTag(index: Int): IntArrayTag {
        return list[index] as IntArrayTag
    }

    fun getIntArray(index: Int): Array<Int> {
        return getIntArrayTag(index).value
    }

    fun getLongArrayTag(index: Int): LongArrayTag {
        return list[index] as LongArrayTag
    }

    fun getLongArray(index: Int): Array<Long> {
        return getLongArrayTag(index).value
    }

    fun getTag(index: Int): INamedTag {
        return list[index]
    }

    fun getAllTag(): Array<INamedTag> {
        return list.toTypedArray()
    }

    fun removeTag(tag: INamedTag): Boolean {
        return list.remove(tag)
    }

    fun removeTag(index: Int) {
        list.removeAt(index)
    }

    operator fun get(index: Int): INamedTag {
        return list[index]
    }

    operator fun set(index: Int, tag: INamedTag) {
        list[index] = tag
    }

    fun size(): Int {
        return list.size
    }

    fun contains(tag: INamedTag): Boolean {
        return list.contains(tag)
    }

    fun removeAllTag() {
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