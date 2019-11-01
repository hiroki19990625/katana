package jp.katana.server.block

import jp.katana.core.block.IBlockDefine
import jp.katana.core.block.IBlockDefinitions
import jp.katana.nbt.Endian
import jp.katana.nbt.io.NBTIO
import org.apache.logging.log4j.LogManager
import java.io.IOException

class BlockDefinitions : IBlockDefinitions {
    private val defines: MutableList<IBlockDefine> = mutableListOf()
    private var prevSize: Int = 0
    private var binaryData: ByteArray = ByteArray(0)

    init {
        // TODO: ParseData...
        val stream =
            this::class.java.classLoader.getResourceAsStream("runtime_block_ids.dat") ?: throw IOException()
        binaryData = stream.readBytes()
        LogManager.getLogger().info(NBTIO.readTag(binaryData, Endian.Little, true).toString())
    }

    override fun fromRuntime(runtimeId: Int): IBlockDefine {
        return defines.first { define -> define.runtimeId == runtimeId }
    }

    override fun fromId(id: Int): IBlockDefine {
        return defines.first { define -> define.id == id.toShort() }
    }

    override fun fromIdAndData(id: Int, data: Int): IBlockDefine {
        return defines.first { define -> define.id == id.toShort() && define.data == data.toShort() }
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
        return binaryData
    }
}