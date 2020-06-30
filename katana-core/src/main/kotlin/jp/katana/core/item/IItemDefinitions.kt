package jp.katana.core.item

interface IItemDefinitions {
    fun fromRuntime(runtimeId: Int): IItemDefine
    fun fromId(id: Int): IItemDefine
    fun fromName(name: String): IItemDefine

    fun register(itemDefine: IItemDefine)

    fun size(): Int

    fun binary(): ByteArray
}