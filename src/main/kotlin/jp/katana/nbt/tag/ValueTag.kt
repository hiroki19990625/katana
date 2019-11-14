package jp.katana.nbt.tag

abstract class ValueTag<T> : INamedTag {
    abstract override var name: String
    abstract override val type: Byte
    abstract var value: T

    override fun toString(): String {
        val builder = StringBuilder()
        print(builder, 0)
        return builder.toString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent(indent)
        builder.append("${this.javaClass.simpleName} : $name = $value\n")
    }
}