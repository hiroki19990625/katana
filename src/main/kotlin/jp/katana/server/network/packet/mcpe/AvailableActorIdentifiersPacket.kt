package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class AvailableActorIdentifiersPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.AVAILABLE_ACTOR_IDENTIFIERS_PACKET

    var tag: ByteArray = ByteArray(0)

    override fun decodePayload() {
        tag = readRemaining()
    }

    override fun encodePayload() {
        write(tag)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendPropertyBufferNetworkNBT(AvailableActorIdentifiersPacket::tag, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}