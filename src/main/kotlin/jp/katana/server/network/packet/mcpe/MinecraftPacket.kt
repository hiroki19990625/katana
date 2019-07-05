package jp.katana.server.network.packet.mcpe

import jp.katana.core.network.packet.mcpe.IMinecraftPacket
import jp.katana.server.utils.BinaryStream

class MinecraftPacket : BinaryStream(), IMinecraftPacket {
    override val channel: Int = 0

    override fun decode() {
        decodeHeader()
        decodePayload()
    }

    protected fun decodeHeader() {

    }

    protected fun decodePayload() {

    }

    override fun encode() {
        encodeHeader()
        encodePayload()
    }

    protected fun encodeHeader() {

    }

    protected fun encodePayload() {

    }
}