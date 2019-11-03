package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class PlayStatusPacket : MinecraftPacket() {
    companion object {
        const val LOGIN_SUCCESS = 0
        const val LOGIN_FAILED_CLIENT = 1
        const val LOGIN_FAILED_SERVER = 2
        const val PLAYER_SPAWN = 3
        const val LOGIN_FAILED_INVALID_TENANT = 4
        const val LOGIN_FAILED_VANILLA_EDU = 5
        const val LOGIN_FAILED_EDU_VANILLA = 6
        const val LOGIN_FAILED_SERVER_FULL = 7
    }

    override val packetId: Int = MinecraftProtocols.PLAY_STATUS_PACKET

    var status = -1

    override fun decodePayload() {
        status = readInt()
    }

    override fun encodePayload() {
        writeInt(status)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }
}