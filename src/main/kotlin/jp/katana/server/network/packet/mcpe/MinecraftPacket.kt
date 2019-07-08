package jp.katana.server.network.packet.mcpe

import jp.katana.core.network.packet.mcpe.IMinecraftPacket
import jp.katana.server.utils.BinaryStream

abstract class MinecraftPacket : BinaryStream(), IMinecraftPacket, Cloneable {
    companion object {
        const val channel_none = 0
        const val channel_priority = 1
        const val channel_chunk = 2
        const val channel_movement = 3
        const val channel_blocks = 4
        const val channel_events = 5
        const val channel_spawning = 6
        const val channel_text = 7
    }

    override val packetId: Int = 0
    override val channel: Int = 0

    override fun decode() {
        decodeHeader()
        decodePayload()
    }

    protected open fun decodeHeader() {
        readUnsignedVarInt()
    }

    protected open fun decodePayload() {

    }

    override fun encode() {
        encodeHeader()
        encodePayload()
    }

    protected open fun encodeHeader() {
        writeUnsignedVarInt(packetId)
    }

    protected open fun encodePayload() {

    }
}