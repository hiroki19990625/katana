package jp.katana.nbt.io

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import jp.katana.nbt.tag.*

class NBTJsonConverter {
    companion object {
        fun jsonToNBT(json: String): CompoundTag {
            val obj = JsonParser().parse(json)
            if (obj.isJsonObject) {
                return convertObject("", obj.asJsonObject)
            }

            return CompoundTag("")
        }

        private fun convertObject(name: String, obj: JsonObject): CompoundTag {
            val tag = CompoundTag(name)
            for (entry in obj.entrySet()) {
                val element = entry.value
                when {
                    element.isJsonObject -> tag.putTag(convertObject(entry.key, element.asJsonObject))
                    element.isJsonArray -> tag.putTag(convertArray(entry.key, element.asJsonArray))
                    element.isJsonPrimitive -> tag.putTag(convertPrimitive(entry.key, element.asJsonPrimitive))
                }
            }

            return tag
        }

        private fun convertArray(name: String, array: JsonArray): ListTag {
            val sample = (if (array.size() == 0) null else array[0]) ?: return ListTag(name)
            val sampleTag = when {
                sample.isJsonObject -> convertObject("", sample.asJsonObject)
                sample.isJsonArray -> convertArray("", sample.asJsonArray)
                sample.isJsonPrimitive -> convertPrimitive("", sample.asJsonPrimitive)
                else -> EndTag()
            }
            val tag = ListTag(name, sampleTag.type)
            for (t in array) {
                when {
                    t.isJsonObject -> tag.addTag(convertObject("", t.asJsonObject))
                    t.isJsonArray -> tag.addTag(convertArray("", t.asJsonArray))
                    t.isJsonPrimitive -> tag.addTag(convertPrimitive("", t.asJsonPrimitive))
                }
            }

            return tag
        }

        private fun convertPrimitive(name: String, value: JsonPrimitive): INamedTag {
            return when {
                value.isBoolean -> ByteTag(name, if (value.asBoolean) 1 else 0)
                value.isNumber -> if (value.asString.contains('.')) DoubleTag(name, value.asDouble) else IntTag(
                    name,
                    value.asInt
                )
                value.isString -> {
                    val vl = value.asString
                    return when {
                        vl.last().toLowerCase() == 'b' -> ByteTag(
                            name,
                            vl.toLowerCase().removeSuffix("b").toByte()
                        )
                        vl.last().toLowerCase() == 's' -> ShortTag(
                            name,
                            vl.toLowerCase().removeSuffix("s").toShort()
                        )
                        vl.last().toLowerCase() == 'l' -> LongTag(
                            name,
                            vl.toLowerCase().removeSuffix("l").toLong()
                        )
                        vl.last().toLowerCase() == 'f' -> FloatTag(
                            name,
                            vl.toLowerCase().removeSuffix("f").toFloat()
                        )
                        else -> StringTag(name, vl)
                    }
                }
                else -> EndTag()
            }
        }

        fun nbtToJson(tag: CompoundTag): String {
            TODO("not impl")
        }
    }
}