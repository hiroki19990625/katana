package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.math.Vector3

class AddActorPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.ADD_ACTOR_PACKET

    var actorUniqueId: Long = 0
    var actorRuntimeId: Long = 0
    var type = ""
    var position = Vector3.ZERO
    var motion = Vector3.ZERO
    var pitch: Float = 0f
    var yaw: Float = 0f
    var headYaw: Float = 0f
    // metadata
    // attributes
    // link

    override fun decodePayload() {
        actorUniqueId = readActorUniqueId()
        actorRuntimeId = readActorRuntimeId()
        type = readVarString()
        position = readVector3()
        motion = readVector3()
        pitch = readFloatLE()
        yaw = readFloatLE()
        headYaw = readFloatLE()
        // metadata
        // attributes
        // link
    }

    override fun encodePayload() {
        writeActorUniqueId(actorUniqueId)
        writeActorRuntimeId(actorRuntimeId)
        writeVarString(type)
        writeVector3(position)
        writeVector3(motion)
        writeFloatLE(pitch)
        writeFloatLE(yaw)
        writeFloatLE(headYaw)
        // metadata
        // attributes
        // link
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(AddActorPacket::actorUniqueId, this, indent + 1)
        builder.appendProperty(AddActorPacket::actorRuntimeId, this, indent + 1)
        builder.appendProperty(AddActorPacket::type, this, indent + 1)
        builder.appendProperty(AddActorPacket::position, this, indent + 1)
        builder.appendProperty(AddActorPacket::motion, this, indent + 1)
        builder.appendProperty(AddActorPacket::pitch, this, indent + 1)
        builder.appendProperty(AddActorPacket::yaw, this, indent + 1)
        builder.appendProperty(AddActorPacket::headYaw, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}