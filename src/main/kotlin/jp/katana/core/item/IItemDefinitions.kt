package jp.katana.core.item

interface IItemDefinitions {
    fun fromRuntime(runtimeId: Int): IItemDefine
    fun fromId(id: Int): IItemDefine

    fun register(runtimeId: Int, itemDefine: IItemDefine)

    fun size(): Int

    fun binary(): ByteArray
}