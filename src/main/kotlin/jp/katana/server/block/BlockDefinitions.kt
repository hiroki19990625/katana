package jp.katana.server.block

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import jp.katana.core.block.IBlockDefine
import jp.katana.core.block.IBlockDefinitions
import jp.katana.utils.BinaryStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class BlockDefinitions : IBlockDefinitions {
    private val defines: MutableList<IBlockDefine> = mutableListOf()
    private var prevSize: Int = 0
    private var binaryData: ByteArray = ByteArray(0)

    init {
        val parser = JsonParser()
        val stream = this::class.java.classLoader.getResourceAsStream("runtime_block_ids.json") ?: throw IOException()

        val element = parser.parse(JsonReader(InputStreamReader(stream, StandardCharsets.UTF_8)))
        if (element is JsonArray) {
            var id = 0
            element.forEach { el ->
                run {
                    if (el is JsonObject) {
                        defines.add(
                            BlockDefine(
                                id++,
                                el.getAsJsonPrimitive("name").asString,
                                el.getAsJsonPrimitive("id").asShort,
                                el.getAsJsonPrimitive("data").asShort
                            )
                        )
                    }
                }
            }
        }
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
        return if (prevSize != defines.size) {
            val stream = BinaryStream()
            defines.forEach { define ->
                run {
                    stream.writeVarString(define.name)
                    stream.writeShortLE(define.data.toInt())
                    stream.writeShortLE(define.id.toInt())
                }
            }

            binaryData = stream.array()
            stream.clear()
            stream.buffer().release()
            binaryData
        } else {
            binaryData
        }
    }
}