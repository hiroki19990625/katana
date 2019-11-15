package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class TakeItemActorPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.TAKE_ITEM_ACTOR_PACKET

    var actorRuntimeId: Long = 0
    var targetRuntimeId: Long = 0

    override fun decodePayload() {
        actorRuntimeId = readActorRuntimeId()
        targetRuntimeId = readActorRuntimeId()
    }

    override fun encodePayload() {
        writeActorRuntimeId(actorRuntimeId)
        writeActorRuntimeId(targetRuntimeId)
    }

    override fun handle(player: IActorPlayer, server: IServer) {

    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(TakeItemActorPacket::actorRuntimeId, this, indent + 1)
        builder.appendProperty(TakeItemActorPacket::targetRuntimeId, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}