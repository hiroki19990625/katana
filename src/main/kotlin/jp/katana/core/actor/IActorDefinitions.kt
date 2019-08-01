package jp.katana.core.actor

interface IActorDefinitions {
    fun fromId(id: Int): IActorDefine
    fun fromName(name: String): IActorDefine

    fun register(actorDefine: IActorDefine)

    fun size(): Int

    fun binary(): ByteArray
}