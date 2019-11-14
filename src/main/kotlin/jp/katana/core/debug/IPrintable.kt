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
        for (i in 0..indent)
            append('\t')
    }

    fun <I, T> StringBuilder.appendProperty(property: KProperty1<I, T>, instance: I, indent: Int) {
        appendIndent(indent)
        append("${property.name} = ${property.get(instance).toString()}\n")
    }

    fun <I> StringBuilder.appendPropertyBufferNetworkNBT(property: KProperty1<I, ByteArray>, instance: I, indent: Int) {
        appendIndent(indent)
        append("${property.name} = ${NBTIO.read(property.get(instance), Endian.Little, true)}\n")
    }
}