package jp.katana.server.network.packet.mcpe

import jp.katana.core.network.packet.mcpe.IMinecraftPacket
import jp.katana.utils.BinaryStream

abstract class MinecraftPacket : BinaryStream(), IMinecraftPacket, Cloneable {
    companion object {
        const val CHANNEL_NONE = 0
        const val CHANNEL_PRIORITY = 1
        const val CHANNEL_CHUNK = 2
        const val CHANNEL_MOVEMENT = 3
        const val CHANNEL_BLOCKS = 4
        const val CHANNEL_EVENTS = 5
        const val CHANNEL_SPAWNING = 6
        const val CHANNEL_TEXT = 7
    }

    override val packetId: Int = MinecraftProtocols.NONE
    override val channel: Int = CHANNEL_NONE

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