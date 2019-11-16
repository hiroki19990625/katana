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

fun CompoundTag.comByte(name: String, value: Byte): ByteTag {
    val tag = ByteTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.comShort(name: String, value: Short): ShortTag {
    val tag = ShortTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.comInt(name: String, value: Int): IntTag {
    val tag = IntTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.comLong(name: String, value: Short): ShortTag {
    val tag = ShortTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.comFloat(name: String, value: Float): FloatTag {
    val tag = FloatTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.comDouble(name: String, value: Double): DoubleTag {
    val tag = DoubleTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.comString(name: String, value: String): StringTag {
    val tag = StringTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.comByteArray(name: String, value: ByteArray): ByteArrayTag {
    val tag = ByteArrayTag(name, value.toTypedArray())
    this.putTag(tag)
    return tag
}

fun CompoundTag.comByteArray(name: String, value: Array<Byte>): ByteArrayTag {
    val tag = ByteArrayTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.comIntArray(name: String, value: IntArray): IntArrayTag {
    val tag = IntArrayTag(name, value.toTypedArray())
    this.putTag(tag)
    return tag
}

fun CompoundTag.comIntArray(name: String, value: Array<Int>): IntArrayTag {
    val tag = IntArrayTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.comLongArray(name: String, value: LongArray): LongArrayTag {
    val tag = LongArrayTag(name, value.toTypedArray())
    this.putTag(tag)
    return tag
}

fun CompoundTag.comLongArray(name: String, value: Array<Long>): LongArrayTag {
    val tag = LongArrayTag(name, value)
    this.putTag(tag)
    return tag
}

fun CompoundTag.comCompound(name: String, init: CompoundTag.() -> Unit): CompoundTag {
    val tag = CompoundTag(name)
    this.putTag(tag)
    tag.apply(init)
    return tag
}

fun CompoundTag.comList(name: String, listType: Byte, init: ListTag.() -> Unit): ListTag {
    val tag = ListTag(name, listType)
    this.putTag(tag)
    tag.apply(init)
    return tag
}

fun ListTag.listByte(value: Byte): ByteTag {
    val tag = ByteTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.listShort(value: Short): ShortTag {
    val tag = ShortTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.listInt(value: Int): IntTag {
    val tag = IntTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.listLong(value: Long): LongTag {
    val tag = LongTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.listFloat(value: Float): FloatTag {
    val tag = FloatTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.listDouble(value: Double): DoubleTag {
    val tag = DoubleTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.listString(value: String): StringTag {
    val tag = StringTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.listByteArray(value: ByteArray): ByteArrayTag {
    val tag = ByteArrayTag("", value.toTypedArray())
    this.addTag(tag)
    return tag
}

fun ListTag.listByteArray(value: Array<Byte>): ByteArrayTag {
    val tag = ByteArrayTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.listIntArray(value: IntArray): IntArrayTag {
    val tag = IntArrayTag("", value.toTypedArray())
    this.addTag(tag)
    return tag
}

fun ListTag.listIntArray(value: Array<Int>): IntArrayTag {
    val tag = IntArrayTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.listLongArray(value: LongArray): LongArrayTag {
    val tag = LongArrayTag("", value.toTypedArray())
    this.addTag(tag)
    return tag
}

fun ListTag.listLongArray(value: Array<Long>): LongArrayTag {
    val tag = LongArrayTag("", value)
    this.addTag(tag)
    return tag
}

fun ListTag.listCompound(init: CompoundTag.() -> Unit): CompoundTag {
    val tag = CompoundTag("")
    this.addTag(tag)
    tag.apply(init)
    return tag
}

fun ListTag.listList(listType: Byte, init: ListTag.() -> Unit): ListTag {
    val tag = ListTag("", listType)
    this.addTag(tag)
    tag.apply(init)
    return tag
}

