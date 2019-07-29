package jp.katana.core.block

interface IBlockDefinitions {
    fun fromRuntime(runtimeId: Int)
    fun fromId(id: Int)
    fun fromIdAndData(id: Int, data: Int)

    fun register(runtimeId: Int, blockDefine: IBlockDefine)

    fun size(): Int

    fun binary(): ByteArray
}