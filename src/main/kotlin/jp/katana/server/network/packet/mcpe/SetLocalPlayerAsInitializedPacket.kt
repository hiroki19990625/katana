package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.actor.PlayerState
import jp.katana.i18n.I18n
import jp.katana.server.actor.ActorPlayer

class SetLocalPlayerAsInitializedPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.SET_LOCAL_PLAYER_AS_INITIALIZED_PACKET

    var actorId: Long = 0

    override fun decodePayload() {
        actorId = readActorRuntimeId()
    }

    override fun encodePayload() {
        writeActorRuntimeId(actorId)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        if (player is ActorPlayer) {
            server.logger.info(I18n["katana.server.player.join", player.displayName])

            player.state = PlayerState.Joined
            // TODO: Send Chat

            // TODO: Event
        }
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(SetLocalPlayerAsInitializedPacket::actorId, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}