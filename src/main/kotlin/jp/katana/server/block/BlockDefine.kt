package jp.katana.server.block

import jp.katana.core.block.IBlockDefine
import jp.katana.nbt.tag.INamedTag

class BlockDefine(
    override val runtimeId: Int,
    override val name: String,
    override val id: Short,
    override val version: Int,
    override val states: MutableMap<String, INamedTag>
) : IBlockDefine