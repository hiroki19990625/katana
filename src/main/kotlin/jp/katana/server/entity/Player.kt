package jp.katana.server.entity

import jp.katana.core.entity.IPlayer
import jp.katana.server.network.packet.mcpe.MinecraftPacket
import java.net.InetSocketAddress

class Player(override val address: InetSocketAddress) : IPlayer {
    override fun handlePacket(packet: MinecraftPacket) {

    }
}