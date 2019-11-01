package jp.katana.server.block

import jp.katana.core.block.IBlockDefine
import jp.katana.core.block.IBlockDefinitions
import jp.katana.nbt.Endian
import jp.katana.nbt.io.NBTIO
import jp.katana.nbt.tag.INamedTag
import jp.katana.nbt.tag.ListTag
import java.io.IOException

class BlockDefinitions : IBlockDefinitions {
    private val defines: MutableList<IBlockDefine> = mutableListOf()
    private var prevSize: Int = 0
    private var binaryData: ByteArray = ByteArray(0)

    init {
        val stream =
            this::class.java.classLoader.getResourceAsStream("runtime_block_ids.dat") ?: throw IOException()
        binaryData = stream.readBytes()
        val list = NBTIO.readTag(binaryData, Endian.Little, true) as ListTag
        for (i in 0 until list.size()) {
            val com = list.getCompound(i)
            val block = com.getCompound("block")
            val states = mutableMapOf<String, INamedTag>()
            for (state in block.getCompound("states").getAll())
                states[state.key] = state.value
            val define =
                BlockDefine(
                    i,
                    block.getString("name").value,
                    com.getShort("id").value,
                    block.getInt("version").value,
                    states
                )
            defines.add(define)
        }
    }

    override fun fromRuntime(runtimeId: Int): IBlockDefine {
        return defines.first { define -> define.runtimeId == runtimeId }
    }

    override fun fromId(id: Int): IBlockDefine {
        return defines.first { define -> define.id == id.toShort() }
    }

    override fun fromIdAndStates(id: Int, states: MutableMap<String, INamedTag>): IBlockDefine {
        return defines.first { define -> define.id == id.toShort() && define.states == states }
    }

    override fun fromName(name: String): IBlockDefine {
        return defines.first { define -> define.name == name }
    }

    override fun register(blockDefine: IBlockDefine) {
        prevSize = defines.size
        defines.add(blockDefine)
    }

    override fun size(): Int {
        return defines.size
    }

    override fun binary(): ByteArray {
        // TODO: Update Binary...
        return binaryData
    }
}