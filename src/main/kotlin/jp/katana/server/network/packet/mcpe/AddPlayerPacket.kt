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

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }
}