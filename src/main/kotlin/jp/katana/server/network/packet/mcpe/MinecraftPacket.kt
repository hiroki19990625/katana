package jp.katana.server.network.packet.mcpe

import jp.katana.core.network.packet.mcpe.IMinecraftPacket
import jp.katana.server.utils.BinaryStream

abstract class MinecraftPacket : BinaryStream(), IMinecraftPacket, Cloneable {
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