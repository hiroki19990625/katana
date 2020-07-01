package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.actor.attribute.IActorAttributes
import jp.katana.debug.appendIndent
import jp.katana.debug.appendProperty
import jp.katana.server.actor.attribute.ActorAttribute

class UpdateAttributesPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.UPDATE_ATTRIBUTES_PACKET

    var actorId: Long = 0
    var attributes: IActorAttributes? = null

    override fun decodePayload() {
        actorId = readActorRuntimeId()

        val c = readUnsignedVarInt()
        for (i in 1..c) {
            val min = readFloatLE()
            val max = readFloatLE()
            val value = readFloatLE()
            val defaultValue = readFloatLE()
            val name = readVarString()

            attributes!!.setAttribute(ActorAttribute(name, max, min, value, defaultValue))
        }
    }

    override fun encodePayload() {
        writeActorRuntimeId(actorId)

        val list = attributes!!.getAttributes()
        writeUnsignedVarInt(list.size)
        for (attribute in list) {
            writeFloatLE(attribute.minValue)
            writeFloatLE(attribute.maxValue)
            writeFloatLE(attribute.value)
            writeFloatLE(attribute.defaultValue)
            writeVarString(attribute.name)
        }
    }

    override fun handleServer(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(UpdateAttributesPacket::actorId, this, indent + 1)
        builder.appendProperty(UpdateAttributesPacket::attributes, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}