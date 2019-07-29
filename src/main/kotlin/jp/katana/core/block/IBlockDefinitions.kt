package jp.katana.core.block

interface IBlockDefinitions {
    fun fromRuntime(runtimeId: Int): IBlockDefine
    fun fromId(id: Int): IBlockDefine
    fun fromIdAndData(id: Int, data: Int): IBlockDefine
    fun fromName(name: String): IBlockDefine

    fun register(blockDefine: IBlockDefine)

    fun size(): Int

    fun binary(): ByteArray
}