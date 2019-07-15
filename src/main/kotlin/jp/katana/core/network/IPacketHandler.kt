package jp.katana.core.network

import jp.katana.server.network.packet.mcpe.LoginPacket
import jp.katana.server.network.packet.mcpe.MinecraftPacket
import jp.katana.server.network.packet.mcpe.PlayStatusPacket

interface IPacketHandler {
    fun handlePacket(packet: MinecraftPacket)
    fun handleLoginPacket(loginPacket: LoginPacket)
    fun handlePlayStatusPacket(playStatusPacket: PlayStatusPacket)
}