package jp.katana.server.block

import jp.katana.core.block.IBlockDefine

class BlockDefine(
    override val runtimeId: Int,
    override val name: String,
    override val id: Short,
    override val data: Short
) : IBlockDefine