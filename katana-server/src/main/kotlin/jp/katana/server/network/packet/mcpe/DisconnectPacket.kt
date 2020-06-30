package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.debug.appendIndent
import jp.katana.debug.appendProperty

class DisconnectPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.DISCONNECT_PACKET

    var hideDisconnectionScreen = false
    var message: String = ""

    override fun decodePayload() {
        hideDisconnectionScreen = readBoolean()
        message = readVarString()
    }

    override fun encodePayload() {
        writeBoolean(hideDisconnectionScreen)
        writeVarString(message)
    }

    override fun handleServer(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(DisconnectPacket::hideDisconnectionScreen, this, indent + 1)
        builder.appendProperty(DisconnectPacket::message, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}