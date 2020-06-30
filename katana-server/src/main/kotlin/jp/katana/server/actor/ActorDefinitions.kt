package jp.katana.server.actor

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import jp.katana.core.actor.IActorDefine
import jp.katana.core.actor.IActorDefinitions
import jp.katana.nbt.Endian
import jp.katana.nbt.io.NBTIO
import jp.katana.nbt.tag.CompoundTag
import jp.katana.nbt.tag.INamedTag
import jp.katana.nbt.tag.ListTag
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class ActorDefinitions : IActorDefinitions {
    private val defines: MutableList<IActorDefine> = mutableListOf()
    private var prevSize: Int = 0
    private var binaryData: ByteArray = ByteArray(0)

    init {
        val parser = JsonParser()
        val stream = this::class.java.classLoader.getResourceAsStream("actor_identifiers.json") ?: throw IOException()

        val element = parser.parse(JsonReader(InputStreamReader(stream, StandardCharsets.UTF_8)))
        if (element is JsonArray) {
            element.forEach { el ->
                run {
                    if (el is JsonObject) {
                        defines.add(
                            ActorDefine(
                                el.get("hasspawnegg").asByte.toBoolean(),
                                el.get("experimental").asByte.toBoolean(),
                                el.get("summonable").asByte.toBoolean(),
                                el.get("id").asString,
                                el.get("bid").asString,
                                el.get("rid").asInt
                            )
                        )
                    }
                }
            }
        }

        stream.close()
    }

    override fun fromId(id: Int): IActorDefine {
        return defines.first { define -> define.rid == id }
    }

    override fun fromName(name: String): IActorDefine {
        return defines.first { define -> define.id == name }
    }

    override fun register(actorDefine: IActorDefine) {
        prevSize = defines.size
        defines.add(actorDefine)
    }

    override fun size(): Int {
        return defines.size
    }

    override fun binary(): ByteArray {
        return if (prevSize != defines.size) {
            val compound = CompoundTag("")
            val list = ListTag("idlist", INamedTag.COMPOUND)
            for (actor in defines) {
                val define = CompoundTag("")
                define.putByte("hasspawnegg", actor.hasSpawnEgg.toByte())
                define.putByte("experimental", actor.experimental.toByte())
                define.putByte("summonable", actor.summonable.toByte())
                define.putString("id", actor.id)
                define.putString("bid", actor.bid)
                define.putInt("rid", actor.rid)
                list.addTag(define)
            }
            compound.putTag(list)
            prevSize = size()
            binaryData = NBTIO.writeTag(compound, Endian.Little, true)
            binaryData
        } else {
            binaryData
        }
    }

    private fun Boolean.toByte(): Byte = if (this) 1 else 0
    private fun Byte.toBoolean(): Boolean = this == 1.toByte()
}