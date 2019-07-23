package jp.katana.server.network.packet.mcpe

import jp.katana.core.data.IClientData
import jp.katana.core.data.ILoginData
import jp.katana.server.data.ClientData
import jp.katana.server.data.LoginData
import jp.katana.server.utils.BinaryStream
import java.nio.charset.Charset

class LoginPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.LOGIN_PACKET

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
        val client = String(stream.read(clientLen), Charset.forName("utf8"))
        clientData.decode(loginData.publicKey!!, client)

        stream.clear()
        stream.buffer().release()
    }

    override fun encodePayload() {
        //TODO generate jwt
    }
}