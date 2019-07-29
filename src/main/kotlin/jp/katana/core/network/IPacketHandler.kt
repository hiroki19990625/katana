package jp.katana.core.network

import jp.katana.server.network.packet.mcpe.*

interface IPacketHandler {
    fun handlePacket(packet: MinecraftPacket)
    fun handleLoginPacket(loginPacket: LoginPacket) // 0x01
    fun handlePlayStatusPacket(playStatusPacket: PlayStatusPacket) // 0x02
    fun handleServerToClientPacket(serverToClientHandshakePacket: ServerToClientHandshakePacket) // 0x03
    fun handleClientToServerPacket(clientToServerHandshakePacket: ClientToServerHandshakePacket) // 0x04
    fun handleDisconnectPacket(disconnectPacket: DisconnectPacket)// 0x05
    fun handleResourcePacksInfoPacket(resourcePacksInfoPacket: ResourcePacksInfoPacket) // 0x06
    fun handleResourcePackStackPacket(resourcePackStackPacket: ResourcePackStackPacket) // 0x07
    fun handleResourcePackClientResponsePacket(resourcePackClientResponsePacket: ResourcePackClientResponsePacket) // 0x08

    fun handleRequestChunkRadiusPacket(requestChunkRadiusPacket: RequestChunkRadiusPacket) // 0x45
    fun handleChunkRadiusUpdatedPacket(chunkRadiusUpdatedPacket: ChunkRadiusUpdatedPacket) // 0x46

    fun handleResourcePackDataInfoPacket(resourcePackDataInfoPacket: ResourcePackDataInfoPacket) // 0x52
    fun handleResourcePackChunkDataPacket(resourcePackChunkDataPacket: ResourcePackChunkDataPacket) // 0x53
    fun handleResourcePackChunkRequestPacket(resourcePackChunkRequestPacket: ResourcePackChunkRequestPacket) // 0x54

    fun handleAvailableEntityIdentifiersPacket(availableEntityIdentifiersPacket: AvailableEntityIdentifiersPacket) // 0x77
    fun handleBiomeDefinitionListPacket(biomeDefinitionListPacket: BiomeDefinitionListPacket) // 0x7a
}