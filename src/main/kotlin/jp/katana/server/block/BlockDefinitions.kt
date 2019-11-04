package jp.katana.server.block

import jp.katana.core.block.IBlockDefine
import jp.katana.core.block.IBlockDefinitions
import jp.katana.nbt.Endian
import jp.katana.nbt.io.NBTIO
import jp.katana.nbt.tag.*
import java.io.IOException

class BlockDefinitions : IBlockDefinitions {
    private val defines: MutableList<IBlockDefine> = mutableListOf()
    private var prevSize: Int = 0
    private var binaryData: ByteArray = ByteArray(0)

    init {
        val stream =
            this::class.java.classLoader.getResourceAsStream("runtime_block_ids.dat") ?: throw IOException()

        val root = NBTIO.readTag(stream.readBytes(), Endian.Big) as CompoundTag
        val list = root.getList("Palette");
        for (i in 0 until list.size()) {
            val com = list.getCompoundTag(i)
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

        prevSize = size()
        binaryData = NBTIO.writeTag(list, Endian.Little, true)
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
        return if (prevSize != defines.size) {
            val list = ListTag("Palette", INamedTag.COMPOUND)
            for (block in defines) {
                val el = CompoundTag("")
                val bt = CompoundTag("block")
                val states = CompoundTag("states")
                for (state in block.states) {
                    states.put(state.value)
                }
                bt.put(StringTag("name", block.name))
                bt.put(IntTag("version", block.version))
                bt.put(states)
                el.put(bt)
                el.put(ShortTag("id", block.id))
                list.add(el)
            }

            prevSize = size()
            binaryData = NBTIO.writeTag(list, Endian.Little, true)
            binaryData
        } else {
            binaryData
        }
    }
}