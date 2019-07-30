package jp.katana.server.network.packet.mcpe

import jp.katana.server.math.Vector3
import java.util.*

class AddPlayerPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.ADD_PLAYER_PACKET

    var uuid: UUID = UUID.randomUUID()
    var username = ""
    var entityUniqueId = 0L
    var entityRuntimeId = 0L
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
    // entity link
    var deviceId = ""

    override fun decodePayload() {
        uuid = readUUID()
        username = readString()
        entityUniqueId = readEntityUniqueId()
        entityRuntimeId = readEntityRuntimeId()
        platformChatId = readString()
        position = readVector3()
        motion = readVector3()
        pitch = readFloatLE()
        yaw = readFloatLE()
        headYaw = readFloatLE()
        // item
        // metadata
        // adventure settings
        long = readLongLE()
        // entity link
        deviceId = readString()
    }

    override fun encodePayload() {
        writeUUID(uuid)
        writeString(username)
        writeEntityUniqueId(entityUniqueId)
        writeEntityRuntimeId(entityRuntimeId)
        writeString(platformChatId)
        writeVector3(position)
        writeVector3(motion)
        writeFloatLE(pitch.toDouble())
        writeFloatLE(yaw.toDouble())
        writeFloatLE(headYaw.toDouble())
        // item
        // metadata
        // adventure settings
        writeLongLE(long)
        // entity link
        writeString(deviceId)
    }
}