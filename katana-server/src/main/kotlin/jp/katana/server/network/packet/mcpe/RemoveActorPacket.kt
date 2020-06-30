package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.utils.readActorUniqueId
import jp.katana.core.utils.writeActorUniqueId
import jp.katana.debug.appendIndent
import jp.katana.debug.appendProperty

class RemoveActorPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.REMOVE_ACTOR_PACKET

    var actorUniqueId: Long = 0

    override fun decodePayload() {
        actorUniqueId = readActorUniqueId()
    }

    override fun encodePayload() {
        writeActorUniqueId(actorUniqueId)
    }

    override fun handleServer(player: IActorPlayer, server: IServer) {

    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(RemoveActorPacket::actorUniqueId, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}