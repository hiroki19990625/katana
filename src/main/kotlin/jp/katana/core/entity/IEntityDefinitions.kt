package jp.katana.core.entity

interface IEntityDefinitions {
    fun fromId(id: Int): IEntityDefine
    fun fromName(name: String): IEntityDefine

    fun register(entityDefine: IEntityDefine)

    fun size(): Int

    fun binary(): ByteArray
}