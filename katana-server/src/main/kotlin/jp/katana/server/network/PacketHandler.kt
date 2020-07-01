package jp.katana.server.network

import jp.katana.core.network.IPacketHandler
import jp.katana.core.network.packet.mcpe.IMinecraftPacket
import jp.katana.server.Server
import jp.katana.server.actor.ActorPlayer


class PacketHandler(private val player: ActorPlayer, private val server: Server) : IPacketHandler {
    override fun handlePacket(packet: IMinecraftPacket) {
        packet.handleServer(player, server)
    }
}