package jp.katana.core.network

import jp.katana.server.network.packet.mcpe.*

interface IPacketHandler {
    fun handlePacket(packet: MinecraftPacket)
    fun handleLoginPacket(loginPacket: LoginPacket)
    fun handlePlayStatusPacket(playStatusPacket: PlayStatusPacket)
    fun handleServerToClientPacket(serverToClientHandshakePacket: ServerToClientHandshakePacket)
    fun handleClientToServerPacket(clientToServerHandshakePacket: ClientToServerHandshakePacket)
    fun handleDisconnectPacket(disconnectPacket: DisconnectPacket)
    fun handleResourcePacksInfoPacket(resourcePacksInfoPacket: ResourcePacksInfoPacket)
    fun handleResourcePackStackPacket(resourcePackStackPacket: ResourcePackStackPacket)
    fun handleResourcePackClientResponsePacket(resourcePackClientResponsePacket: ResourcePackClientResponsePacket)
}