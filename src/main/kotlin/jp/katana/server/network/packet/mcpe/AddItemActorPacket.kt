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

    }
}