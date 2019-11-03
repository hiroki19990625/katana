package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

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

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }
}