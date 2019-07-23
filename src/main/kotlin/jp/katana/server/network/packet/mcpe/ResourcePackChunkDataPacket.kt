package jp.katana.server.network.packet.mcpe

import org.apache.logging.log4j.LogManager

class ResourcePackChunkDataPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.RESOURCE_PACK_CHUNK_DATA_PACKET

    var packId: String = ""
    var chunkIndex = 0
    var progress: Long = 0
    var data: ByteArray = ByteArray(0)

    override fun decodePayload() {
        packId = readString()
        chunkIndex = readIntLE()
        progress = readLongLE()
        data = read(readIntLE())
    }

    override fun encodePayload() {
        writeString(packId)
        writeIntLE(chunkIndex)
        writeLongLE(progress)
        writeIntLE(data.size)
        write(data)
    }
}