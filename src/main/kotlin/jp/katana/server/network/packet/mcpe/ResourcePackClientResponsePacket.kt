package jp.katana.server.network.packet.mcpe

import jp.katana.core.data.IResourcePackEntry
import jp.katana.server.data.ResourcePackEntry
import java.util.*

class ResourcePackClientResponsePacket : MinecraftPacket() {
    companion object {
        const val STATUS_REFUSED: Byte = 1
        const val STATUS_SEND_PACKS: Byte = 2
        const val STATUS_HAVE_ALL_PACKS: Byte = 3
        const val STATUS_COMPLETED: Byte = 4
    }

    override val packetId: Int = MinecraftProtocols.RESOURCE_PACK_CLIENT_RESPONSE_PACKET

    var status: Byte = 0
    val packEntries = mutableListOf<IResourcePackEntry>()

    override fun decodePayload() {
        status = readByte()
        val len = readShortLE()
        for (entry in 1..len) {
            packEntries.add(ResourcePackEntry(UUID.fromString(readString()), readString()))
        }
    }

    override fun encodePayload() {
        writeByte(status.toInt())
        writeShortLE(packEntries.size)
        for (entry in packEntries) {
            writeString(entry.uuid.toString())
            writeString(entry.version)
        }
    }
}