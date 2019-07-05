package jp.katana.core.network

import jp.katana.core.entity.IPlayer
import jp.katana.server.network.packet.mcpe.MinecraftPacket
import java.net.InetSocketAddress

interface INetworkManager {
    fun start()
    fun shutdown()

    fun addPlayer(address: InetSocketAddress, player: IPlayer): Boolean
    fun removePlayer(address: InetSocketAddress): Boolean
    fun getPlayer(address: InetSocketAddress): IPlayer?

    fun sendPacket(player: IPlayer, packet: MinecraftPacket, reliability: Reliability = Reliability.RELIABLE_ORDERED)
    fun handlePacket(address: InetSocketAddress, packet: MinecraftPacket)
}