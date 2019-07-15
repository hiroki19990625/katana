package jp.katana.core.entity

import jp.katana.core.data.IClientData
import jp.katana.core.data.ILoginData
import jp.katana.core.network.IPacketHandler
import jp.katana.core.network.Reliability
import jp.katana.server.network.packet.mcpe.MinecraftPacket
import java.net.InetSocketAddress

interface IPlayer {
    val address: InetSocketAddress
    val packetHandler: IPacketHandler

    val loginData: ILoginData?
    val clientData: IClientData?

    fun handlePacket(packet: MinecraftPacket)
    fun sendPacket(packet: MinecraftPacket, reliability: Reliability = Reliability.RELIABLE_ORDERED)
}