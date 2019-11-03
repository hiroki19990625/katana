package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class ResourcePackChunkRequestPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.RESOURCE_PACK_CHUNK_REQUEST_PACKET

    var packId: String = ""
    var chunkIndex: Int = 0

    override fun decodePayload() {
        packId = readVarString()
        chunkIndex = readIntLE()
    }

    override fun encodePayload() {
        writeVarString(packId)
        writeIntLE(chunkIndex)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        val pack = server.resourcePackManager.getResourcePack(packId)
        if (pack == null) {
            player.disconnect("disconnectionScreen.resourcePack")
            return
        }

        val index = chunkIndex
        val mb1 = ResourcePackDataInfoPacket.MB_1
        val offset = mb1 * index
        val resourcePackChunkDataPacket = ResourcePackChunkDataPacket()
        resourcePackChunkDataPacket.packId = pack.packId
        resourcePackChunkDataPacket.chunkIndex = index
        resourcePackChunkDataPacket.data = pack.getDataChunk(offset, mb1)
        resourcePackChunkDataPacket.progress = offset.toLong()

        player.sendPacket(resourcePackChunkDataPacket)
    }
}