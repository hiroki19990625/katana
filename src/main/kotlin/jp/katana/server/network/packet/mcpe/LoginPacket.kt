package jp.katana.server.network.packet.mcpe

import jp.katana.server.utils.BinaryStream
import org.apache.logging.log4j.LogManager
import java.nio.charset.Charset

class LoginPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.loginPacket
    var protocol: Int = 0

    override fun decodePayload() {
        protocol = readInt()

        val len = readUnsignedVarInt()
        val stream = BinaryStream()
        stream.setBuffer(read(len))

        val chainLen = stream.readIntLE()
        val chain = String(stream.read(chainLen), Charset.forName("utf8"))

        val clientLen = stream.readIntLE()
        val client = String(stream.read(clientLen), Charset.forName("utf8"))

        LogManager.getLogger().info(chain)
        LogManager.getLogger().info(client)
    }

    override fun encodePayload() {

    }
}