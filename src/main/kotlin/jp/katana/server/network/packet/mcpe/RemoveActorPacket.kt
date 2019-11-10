package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class RemoveActorPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.REMOVE_ACTOR_PACKET

    var actorUniqueId: Long = 0

    override fun decodePayload() {
        actorUniqueId = readActorUniqueId()
    }

    override fun encodePayload() {
        writeActorUniqueId(actorUniqueId)
    }

    override fun handle(player: IActorPlayer, server: IServer) {

    }
}