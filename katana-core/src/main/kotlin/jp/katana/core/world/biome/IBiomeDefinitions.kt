package jp.katana.core.world.biome

interface IBiomeDefinitions {
    fun fromName(name: String): IBiomeDefine

    fun register(blockDefine: IBiomeDefine)

    fun size(): Int

    fun binary(): ByteArray
}