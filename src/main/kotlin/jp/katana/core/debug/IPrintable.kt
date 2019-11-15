package jp.katana.core.debug

import jp.katana.nbt.Endian
import jp.katana.nbt.io.NBTIO
import kotlin.reflect.KProperty1

interface IPrintable {
    fun print(builder: StringBuilder, indent: Int)

    fun IPrintable.toPrintString(): String {
        val builder = StringBuilder()
        print(builder, 0)
        return builder.toString()
    }

    fun StringBuilder.appendIndent(indent: Int) {
        for (i in 1..indent)
            append('\t')
    }

    fun StringBuilder.appendIndent(msg: String, indent: Int) {
        appendIndent(indent)
        append(msg)
    }

    fun <I, T> StringBuilder.appendProperty(property: KProperty1<I, T>, instance: I, indent: Int) {
        appendIndent("${property.name} = ${property.get(instance).toString()}\n", indent)
    }

    fun <I, T> StringBuilder.appendArrayProperty(property: KProperty1<I, Array<T>>, instance: I, indent: Int) {
        // TODO: ArrayDumping
        appendIndent("${property.name} = Length: ${property.get(instance).size}\n", indent)
    }

    fun <I> StringBuilder.appendByteArrayProperty(property: KProperty1<I, ByteArray>, instance: I, indent: Int) {
        // TODO: ArrayDumping
        appendIndent("${property.name} = Length: ${property.get(instance).size}\n", indent)
    }

    fun <I> StringBuilder.appendIntArrayProperty(property: KProperty1<I, IntArray>, instance: I, indent: Int) {
        // TODO: ArrayDumping
        appendIndent("${property.name} = Length: ${property.get(instance).size}\n", indent)
    }

    fun <I> StringBuilder.appendLongArrayProperty(property: KProperty1<I, LongArray>, instance: I, indent: Int) {
        // TODO: ArrayDumping
        appendIndent("${property.name} = Length: ${property.get(instance).size}\n", indent)
    }

    fun <I, V> StringBuilder.appendListProperty(property: KProperty1<I, List<V>>, instance: I, indent: Int) {
        // TODO: ArrayDumping
        appendIndent("${property.name} = Length: ${property.get(instance).size}\n", indent)
    }

    fun <I, K, V> StringBuilder.appendMapProperty(property: KProperty1<I, Map<K, V>>, instance: I, indent: Int) {
        // TODO: ArrayDumping
        appendIndent("${property.name} = Length: ${property.get(instance).size}\n", indent)
    }

    fun <I> StringBuilder.appendPropertyBufferNetworkNBT(property: KProperty1<I, ByteArray>, instance: I, indent: Int) {
        appendIndent("${property.name} = {\n", indent)
        NBTIO.readTag(property.get(instance), Endian.Little, true).print(this, indent + 1)
        appendIndent("}", indent)
    }
}