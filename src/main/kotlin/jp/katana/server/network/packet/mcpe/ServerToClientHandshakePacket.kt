package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class ServerToClientHandshakePacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.SERVER_TO_CLIENT_HANDSHAKE_PACKET

    var token: String = ""

    override fun decodePayload() {
        token = readVarString()
    }

    override fun encodePayload() {
        writeVarString(token)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.append("${this.javaClass.simpleName} {\n")
        builder.append("}\n")
    }
}