package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.math.Vector3

class MovePlayerPacket : MinecraftPacket() {
    companion object {
        const val MODE_NORMAL: Byte = 0
        const val MODE_RESET: Byte = 1
        const val MODE_TELEPORT: Byte = 2
        const val MODE_PITCH: Byte = 3
    }

    override val packetId: Int = MinecraftProtocols.MOVE_PLAYER_PACKET

    var actorRuntimeId: Long = 0
    var position = Vector3.ZERO
    var pitch: Float = 0f
    var yaw: Float = 0f
    var headYaw: Float = 0f
    var mode: Byte = MODE_NORMAL
    var onGround = false
    var ridingRuntimeId: Long = 0
    var teleportCause = 0
    var teleportItem = 0

    override fun decodePayload() {
        actorRuntimeId = readActorRuntimeId()
        position = readVector3()
        pitch = readFloatLE()
        yaw = readFloatLE()
        headYaw = readFloatLE()
        mode = readByte()
        onGround = readBoolean()
        ridingRuntimeId = readActorRuntimeId()
        if (mode == MODE_TELEPORT) {
            teleportCause = readIntLE()
            teleportItem = readIntLE()
        }
    }

    override fun encodePayload() {
        writeActorRuntimeId(actorRuntimeId)
        writeVector3(position)
        writeFloatLE(pitch)
        writeFloatLE(yaw)
        writeFloatLE(headYaw)
        writeByte(mode)
        writeBoolean(onGround)
        writeActorRuntimeId(ridingRuntimeId)
        if (mode == MODE_TELEPORT) {
            writeIntLE(teleportCause)
            writeIntLE(teleportItem)
        }
    }

    override fun handleServer(player: IActorPlayer, server: IServer) {

    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(MovePlayerPacket::actorRuntimeId, this, indent + 1)
        builder.appendProperty(MovePlayerPacket::position, this, indent + 1)
        builder.appendProperty(MovePlayerPacket::pitch, this, indent + 1)
        builder.appendProperty(MovePlayerPacket::yaw, this, indent + 1)
        builder.appendProperty(MovePlayerPacket::headYaw, this, indent + 1)
        builder.appendProperty(MovePlayerPacket::mode, this, indent + 1)
        builder.appendProperty(MovePlayerPacket::onGround, this, indent + 1)
        builder.appendProperty(MovePlayerPacket::ridingRuntimeId, this, indent + 1)
        builder.appendProperty(MovePlayerPacket::teleportCause, this, indent + 1)
        builder.appendProperty(MovePlayerPacket::teleportItem, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}
