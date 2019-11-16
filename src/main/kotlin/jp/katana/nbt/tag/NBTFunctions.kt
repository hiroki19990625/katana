package jp.katana.nbt.tag

fun nCompound(name: String, init: CompoundTag.() -> Unit): CompoundTag {
    val tag = CompoundTag(name)
    tag.apply(init)
    return tag
}

fun nList(name: String, init: ListTag.() -> Unit): ListTag {
    val tag = ListTag(name)
    tag.apply(init)
    return tag
}

fun CompoundTag.tByte(name: String, value: Byte): ByteTag {
    val tag = ByteTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.tShort(name: String, value: Short): ShortTag {
    val tag = ShortTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.tInt(name: String, value: Int): IntTag {
    val tag = IntTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.tLong(name: String, value: Short): ShortTag {
    val tag = ShortTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.tFloat(name: String, value: Float): FloatTag {
    val tag = FloatTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.tDouble(name: String, value: Double): DoubleTag {
    val tag = DoubleTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.tString(name: String, value: String): StringTag {
    val tag = StringTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.tByteArray(name: String, value: ByteArray): ByteArrayTag {
    val tag = ByteArrayTag(name, value.toTypedArray())
    this.putTag(tag)
    return tag
}

fun CompoundTag.tByteArray(name: String, value: Array<Byte>): ByteArrayTag {
    val tag = ByteArrayTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.tIntArray(name: String, value: IntArray): IntArrayTag {
    val tag = IntArrayTag(name, value.toTypedArray())
    this.putTag(tag)
    return tag
}

fun CompoundTag.tIntArray(name: String, value: Array<Int>): IntArrayTag {
    val tag = IntArrayTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.tLongArray(name: String, value: LongArray): LongArrayTag {
    val tag = LongArrayTag(name, value.toTypedArray())
    this.putTag(tag)
    return tag
}

fun CompoundTag.tLongArray(name: String, value: Array<Long>): LongArrayTag {
    val tag = LongArrayTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.tCompound(name: String, init: CompoundTag.() -> Unit): CompoundTag {
    val tag = CompoundTag(name)
    this.putTag(tag)
    tag.apply(init)
    return tag
}

fun CompoundTag.tList(name: String, listType: Byte, init: ListTag.() -> Unit): ListTag {
    val tag = ListTag(name, listType)
    this.putTag(tag)
    tag.apply(init)
    return tag
}

fun ListTag.tByte(value: Byte): ByteTag {
    val tag = ByteTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.tShort(value: Short): ShortTag {
    val tag = ShortTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.tInt(value: Int): IntTag {
    val tag = IntTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.tLong(value: Long): LongTag {
    val tag = LongTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.tFloat(value: Float): FloatTag {
    val tag = FloatTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.tDouble(value: Double): DoubleTag {
    val tag = DoubleTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.tString(value: String): StringTag {
    val tag = StringTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.tByteArray(value: ByteArray): ByteArrayTag {
    val tag = ByteArrayTag("", value.toTypedArray())
    this.addTag(tag)
    return tag
}

fun ListTag.tByteArray(value: Array<Byte>): ByteArrayTag {
    val tag = ByteArrayTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.tIntArray(value: IntArray): IntArrayTag {
    val tag = IntArrayTag("", value.toTypedArray())
    this.addTag(tag)
    return tag
}

fun ListTag.tIntArray(value: Array<Int>): IntArrayTag {
    val tag = IntArrayTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.tLongArray(value: LongArray): LongArrayTag {
    val tag = LongArrayTag("", value.toTypedArray())
    this.addTag(tag)
    return tag
}

fun ListTag.tLongArray(value: Array<Long>): LongArrayTag {
    val tag = LongArrayTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.tCompound(name: String, init: CompoundTag.() -> Unit): CompoundTag {
    val tag = CompoundTag(name)
    this.addTag(tag)
    tag.apply(init)
    return tag
}

fun ListTag.tList(name: String, listType: Byte, init: ListTag.() -> Unit): ListTag {
    val tag = ListTag(name, listType)
    this.addTag(tag)
    tag.apply(init)
    return tag
}

