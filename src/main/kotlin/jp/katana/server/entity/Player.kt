package jp.katana.server.entity

import jp.katana.core.entity.IPlayer
import jp.katana.server.network.packet.mcpe.MinecraftPacket
import org.apache.logging.log4j.LogManager
import java.net.InetSocketAddress

class Player(override val address: InetSocketAddress) : IPlayer {
    private val logger = LogManager.getLogger()

    override fun handlePacket(packet: MinecraftPacket) {
        logger.info(packet.packetId)
    }
}