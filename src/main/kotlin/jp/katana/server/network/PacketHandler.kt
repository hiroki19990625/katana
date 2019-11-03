package jp.katana.server.network

import jp.katana.core.network.IPacketHandler
import jp.katana.server.Server
import jp.katana.server.actor.ActorPlayer
import jp.katana.server.network.packet.mcpe.MinecraftPacket


class PacketHandler(private val player: ActorPlayer, private val server: Server) : IPacketHandler {
    override fun handlePacket(packet: MinecraftPacket) {
        packet.handle(player, server)
    }
}