package jp.katana.core.entity

import jp.katana.server.network.packet.mcpe.MinecraftPacket
import java.net.InetSocketAddress

interface IPlayer {
    val address: InetSocketAddress

    fun handlePacket(packet: MinecraftPacket)
}