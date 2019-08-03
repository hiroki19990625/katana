package jp.katana.nbt.tag

abstract class ArrayTag<T> : INamedTag {
    abstract override var name: String
    abstract override val type: Byte
    abstract var value: Array<T>

    override fun toString(): String {
        return "${this.javaClass.simpleName} : $name = length ${value.size}"
    }
}