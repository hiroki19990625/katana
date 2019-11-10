package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.math.Vector3
import kotlin.experimental.and
import kotlin.experimental.or

class MoveActorAbsolutePacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.MOVE_ACTOR_ABSOLUTE_PACKET

    var actorRuntimeId: Long = 0
    var position = Vector3.ZERO
    var pitch: Float = 0f
    var yaw: Float = 0f
    var headYaw: Float = 0f
    var teleport = false
    var onGround = false

    override fun decodePayload() {
        actorRuntimeId = readActorRuntimeId()
        val flags = readByte()
        teleport = (flags and 0x01) != 0.toByte()
        onGround = (flags and 0x02) != 0.toByte()
        position = readVector3()
        pitch = readByte() * (360f / 256f)
        yaw = readByte() * (360f / 256f)
        headYaw = readByte() * (360f / 256f)
    }

    override fun encodePayload() {
        writeActorRuntimeId(actorRuntimeId)
        var flags: Byte = 0
        if (teleport) flags = flags or 0x01
        if (onGround) flags = flags or 0x02
        writeByte(flags)
        writeVector3(position)
        writeByte((pitch / (360f / 256f)).toByte())
        writeByte((yaw / (360f / 256f)).toByte())
        writeByte((headYaw / (360f / 256f)).toByte())
    }

    override fun handle(player: IActorPlayer, server: IServer) {

    }
}