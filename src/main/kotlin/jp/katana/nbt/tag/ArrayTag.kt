package jp.katana.nbt.tag

abstract class ArrayTag<T> : INamedTag {
    abstract override var name: String
    abstract override val type: Byte
    abstract var value: Array<T>

    override fun toString(): String {
        val builder = StringBuilder()
        print(builder, 0)
        return builder.toString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent(indent)
        builder.append("${this.javaClass.simpleName} : $name = length ${value.size}\n")
    }
}