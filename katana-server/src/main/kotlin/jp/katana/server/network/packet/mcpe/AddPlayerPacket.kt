package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.math.Vector3
import java.util.*

class AddPlayerPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.ADD_PLAYER_PACKET

    var uuid: UUID = UUID.randomUUID()
    var username = ""
    var actorUniqueId = 0L
    var actorRuntimeId = 0L
    var platformChatId = ""
    var position = Vector3.ZERO
    var motion = Vector3.ZERO
    var pitch = 0f
    var yaw = 0f
    var headYaw = 0f
    // item
    // metadata
    // adventure settings
    var long = 0L
    // actor link
    var deviceId = ""

    override fun decodePayload() {
        uuid = readUUID()
        username = readVarString()
        actorUniqueId = readActorUniqueId()
        actorRuntimeId = readActorRuntimeId()
        platformChatId = readVarString()
        position = readVector3()
        motion = readVector3()
        pitch = readFloatLE()
        yaw = readFloatLE()
        headYaw = readFloatLE()
        // item
        // metadata
        // adventure settings
        long = readLongLE()
        // actor link
        deviceId = readVarString()
    }

    override fun encodePayload() {
        writeUUID(uuid)
        writeVarString(username)
        writeActorUniqueId(actorUniqueId)
        writeActorRuntimeId(actorRuntimeId)
        writeVarString(platformChatId)
        writeVector3(position)
        writeVector3(motion)
        writeFloatLE(pitch)
        writeFloatLE(yaw)
        writeFloatLE(headYaw)
        // item
        // metadata
        // adventure settings
        writeLongLE(long)
        // actor link
        writeVarString(deviceId)
    }

    override fun handleServer(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(AddPlayerPacket::uuid, this, indent + 1)
        builder.appendProperty(AddPlayerPacket::username, this, indent + 1)
        builder.appendProperty(AddPlayerPacket::actorUniqueId, this, indent + 1)
        builder.appendProperty(AddPlayerPacket::actorRuntimeId, this, indent + 1)
        builder.appendProperty(AddPlayerPacket::platformChatId, this, indent + 1)
        builder.appendProperty(AddPlayerPacket::position, this, indent + 1)
        builder.appendProperty(AddPlayerPacket::motion, this, indent + 1)
        builder.appendProperty(AddPlayerPacket::pitch, this, indent + 1)
        builder.appendProperty(AddPlayerPacket::yaw, this, indent + 1)
        builder.appendProperty(AddPlayerPacket::headYaw, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}