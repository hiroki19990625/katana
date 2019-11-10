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
        type = readUtf8String()
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
        writeUtf8String(type)
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

    }
}