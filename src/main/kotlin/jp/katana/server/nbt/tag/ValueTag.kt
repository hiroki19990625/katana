package jp.katana.server.nbt.tag

abstract class ValueTag<T> : INamedTag {
    abstract override var name: String
    abstract override val type: Byte
    abstract var value: T

    override fun toString(): String {
        return "${this.javaClass.simpleName} : $name = $value"
    }
}