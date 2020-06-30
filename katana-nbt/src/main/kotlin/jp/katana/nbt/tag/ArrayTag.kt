package jp.katana.nbt.tag

import jp.katana.debug.appendIndent

abstract class ArrayTag<T> : INamedTag {
    abstract override var name: String
    abstract override val type: Byte
    abstract var value: Array<T>

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent(indent)
        builder.append("${this.javaClass.simpleName} : $name = length ${value.size}\n")
    }
}