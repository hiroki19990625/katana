package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class ResourcePackChunkDataPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.RESOURCE_PACK_CHUNK_DATA_PACKET

    var packId: String = ""
    var chunkIndex = 0
    var progress: Long = 0
    var data: ByteArray = ByteArray(0)

    override fun decodePayload() {
        packId = readVarString()
        chunkIndex = readIntLE()
        progress = readLongLE()
        data = read(readUnsignedVarInt())
    }

    override fun encodePayload() {
        writeVarString(packId)
        writeIntLE(chunkIndex)
        writeLongLE(progress)
        writeUnsignedVarInt(data.size)
        write(data)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(ResourcePackChunkDataPacket::packId, this, indent + 1)
        builder.appendProperty(ResourcePackChunkDataPacket::chunkIndex, this, indent + 1)
        builder.appendProperty(ResourcePackChunkDataPacket::progress, this, indent + 1)
        builder.appendByteArrayProperty(ResourcePackChunkDataPacket::data, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}