package jp.katana.server.item

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import jp.katana.core.item.IItemDefine
import jp.katana.core.item.IItemDefinitions
import jp.katana.utils.BinaryStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets


class ItemDefinitions : IItemDefinitions {
    private val defines: MutableList<IItemDefine> = mutableListOf()
    private var prevSize: Int = 0
    private var binaryData: ByteArray = ByteArray(0)

    init {
        val parser = JsonParser()
        val stream = this::class.java.classLoader.getResourceAsStream("runtime_item_ids.json") ?: throw IOException()

        val element = parser.parse(JsonReader(InputStreamReader(stream, StandardCharsets.UTF_8)))
        var id = 0
        if (element is JsonArray) {
            element.forEach { el ->
                run {
                    if (el is JsonObject) {
                        defines.add(
                            ItemDefine(
                                id++,
                                el.getAsJsonPrimitive("name").asString,
                                el.getAsJsonPrimitive("id").asShort
                            )
                        )
                    }
                }
            }
        }
    }

    override fun fromRuntime(runtimeId: Int): IItemDefine {
        return defines.first { define -> define.runtimeId == runtimeId }
    }

    override fun fromId(id: Int): IItemDefine {
        return defines.first { define -> define.id == id.toShort() }
    }

    override fun fromName(name: String): IItemDefine {
        return defines.first { define -> define.name == name }
    }

    override fun register(itemDefine: IItemDefine) {
        prevSize = defines.size
        defines.add(itemDefine)
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
                    stream.writeShortLE(define.id)
                }
            }

            prevSize = size()
            binaryData = stream.array()
            stream.close()
            binaryData
        } else {
            binaryData
        }
    }
}