package jp.katana.core.block

import jp.katana.nbt.tag.INamedTag

interface IBlockDefinitions {
    fun fromRuntime(runtimeId: Int): IBlockDefine
    fun fromId(id: Int): IBlockDefine
    fun fromIdAndStates(id: Int, states: MutableMap<String, INamedTag>): IBlockDefine
    fun fromName(name: String): IBlockDefine

    fun register(blockDefine: IBlockDefine)

    fun size(): Int

    fun binary(): ByteArray
}