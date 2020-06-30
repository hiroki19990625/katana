package jp.katana.core.network

import jp.katana.core.network.packet.mcpe.IMinecraftPacket

interface IPacketHandler {
    fun handlePacket(packet: IMinecraftPacket)
}