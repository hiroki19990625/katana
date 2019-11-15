package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.math.Vector3

class AddItemActorPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.ADD_ITEM_ACTOR_PACKET

    var actorUniqueId: Long = 0
    var actorRuntimeId: Long = 0
    // item
    var position = Vector3.ZERO
    var motion = Vector3.ZERO
    // metadata
    var isFromFishing = false

    override fun decodePayload() {
        actorUniqueId = readActorUniqueId()
        actorRuntimeId = readActorRuntimeId()
        // item
        position = readVector3()
        motion = readVector3()
        // metadata
        isFromFishing = readBoolean()
    }

    override fun encodePayload() {
        writeActorUniqueId(actorUniqueId)
        writeActorRuntimeId(actorRuntimeId)
        // item
        writeVector3(position)
        writeVector3(motion)
        // metadata
        writeBoolean(isFromFishing)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(AddItemActorPacket::actorUniqueId, this, indent + 1)
        builder.appendProperty(AddItemActorPacket::actorRuntimeId, this, indent + 1)
        builder.appendProperty(AddItemActorPacket::position, this, indent + 1)
        builder.appendProperty(AddItemActorPacket::motion, this, indent + 1)
        builder.appendProperty(AddItemActorPacket::isFromFishing, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}