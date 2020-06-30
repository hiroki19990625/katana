package jp.katana.core.debug

import jp.katana.debug.appendIndent
import jp.katana.nbt.Endian
import jp.katana.nbt.io.NBTIO
import kotlin.reflect.KProperty1

fun <I> StringBuilder.appendPropertyBufferNetworkNBT(property: KProperty1<I, ByteArray>, instance: I, indent: Int) {
    appendIndent("${property.name} = {\n", indent)
    NBTIO.readTag(property.get(instance), Endian.Little, true).print(this, indent + 1)
    appendIndent("}", indent)
}