package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class SetTimePacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.SET_TIME_PACKET

    var time = 0

    override fun decodePayload() {
        time = readVarInt()
    }

    override fun encodePayload() {
        writeVarInt(time)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(SetTimePacket::time, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}