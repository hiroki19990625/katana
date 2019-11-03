package jp.katana.core.network

import jp.katana.server.network.packet.mcpe.MinecraftPacket

interface IPacketHandler {
    fun handlePacket(packet: MinecraftPacket)
}