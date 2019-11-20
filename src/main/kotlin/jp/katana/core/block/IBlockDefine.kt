package jp.katana.core.block

import jp.katana.nbt.tag.INamedTag

interface IBlockDefine {
    val runtimeId: Int
    val name: String
    val id: Short
    val version: Int
    val states: Map<String, INamedTag>
}