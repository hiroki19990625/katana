package jp.katana.server.network.packet.mcpe

import jp.katana.core.data.IClientData
import jp.katana.core.data.ILoginData
import jp.katana.server.data.ClientData
import jp.katana.server.data.LoginData
import jp.katana.server.utils.BinaryStream
import org.apache.logging.log4j.LogManager
import java.nio.charset.Charset
import java.util.*

class LoginPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.loginPacket
    var protocol: Int = 0

    var loginData: ILoginData = LoginData()
    var clientData: IClientData = ClientData()

    override fun decodePayload() {
        protocol = readInt()

        val len = readUnsignedVarInt()
        val stream = BinaryStream()
        stream.setBuffer(read(len))

        val chainLen = stream.readIntLE()
        val chain = String(stream.read(chainLen), Charset.forName("utf8"))
        loginData.decode(chain)

        val clientLen = stream.readIntLE()
        val client = stream.read(clientLen)
        loginData.decode(String(Base64.getDecoder().decode(client), Charset.forName("utf8")))

        LogManager.getLogger().info(chain)
        LogManager.getLogger().info(client)
    }

    override fun encodePayload() {

    }
}