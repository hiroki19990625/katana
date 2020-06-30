package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.debug.appendIndent
import jp.katana.debug.appendProperty

class ServerToClientHandshakePacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.SERVER_TO_CLIENT_HANDSHAKE_PACKET

    var token: String = ""

    override fun decodePayload() {
        token = readVarString()
    }

    override fun encodePayload() {
        writeVarString(token)
    }

    override fun handleServer(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(ServerToClientHandshakePacket::token, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}