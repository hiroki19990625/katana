package jp.katana.server.network

import jp.katana.core.network.IPacketHandler
import jp.katana.server.Server
import jp.katana.server.entity.Player
import jp.katana.server.network.packet.mcpe.LoginPacket
import jp.katana.server.network.packet.mcpe.MinecraftPacket
import jp.katana.server.network.packet.mcpe.PlayStatusPacket

class PacketHandler(private val player: Player) : IPacketHandler {
    override fun handlePacket(packet: MinecraftPacket) {
        if (packet is LoginPacket)
            handleLoginPacket(packet)
        else if (packet is PlayStatusPacket)
            handlePlayStatusPacket(packet)
    }

    override fun handleLoginPacket(loginPacket: LoginPacket) {
        val protocol = loginPacket.protocol

        val playStatusPacket = PlayStatusPacket()
        if (protocol > Server.PROTOCOL_VERSION) {
            playStatusPacket.status = PlayStatusPacket.LOGIN_FAILED_SERVER
            player.sendPacket(playStatusPacket)
            return
        }

        if (protocol < Server.PROTOCOL_VERSION) {
            playStatusPacket.status = PlayStatusPacket.LOGIN_FAILED_CLIENT
            player.sendPacket(playStatusPacket)
            return
        }

        player.loginData = loginPacket.loginData
        player.clientData = loginPacket.clientData
    }

    override fun handlePlayStatusPacket(playStatusPacket: PlayStatusPacket) {
    }
}