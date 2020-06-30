package jp.katana.nbt.tag

import jp.katana.debug.appendIndent

abstract class ValueTag<T> : INamedTag {
    abstract override var name: String
    abstract override val type: Byte
    abstract var value: T

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent(indent)
        builder.append("${this.javaClass.simpleName} : $name = $value\n")
    }
}