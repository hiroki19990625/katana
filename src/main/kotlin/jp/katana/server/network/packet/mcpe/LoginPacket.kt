package jp.katana.server.network.packet.mcpe

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.ECDSASigner
import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.jwk.ECKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.actor.PlayerState
import jp.katana.core.data.IClientData
import jp.katana.core.data.ILoginData
import jp.katana.i18n.I18n
import jp.katana.server.actor.ActorPlayer
import jp.katana.server.data.ClientData
import jp.katana.server.data.LoginData
import jp.katana.utils.BinaryStream
import sun.security.ec.ECKeyPairGenerator
import java.net.URI
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyAgreement
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

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

    override fun handle(player: IActorPlayer, server: IServer) {
        if (player is ActorPlayer) {
            val protocol = protocol

            val playStatusPacket = PlayStatusPacket()
            if (protocol > server.protocolVersion) {
                playStatusPacket.status = PlayStatusPacket.LOGIN_FAILED_SERVER
                player.sendPacket(playStatusPacket)
                return
            }

            if (protocol < server.protocolVersion) {
                playStatusPacket.status = PlayStatusPacket.LOGIN_FAILED_CLIENT
                player.sendPacket(playStatusPacket)
                return
            }

            if (server.networkManager != null && server.networkManager!!.getPlayers().size > server.maxPlayer) {
                playStatusPacket.status = PlayStatusPacket.LOGIN_FAILED_SERVER_FULL
                player.sendPacket(playStatusPacket)
                return
            }

            player.loginData = loginData
            player.clientData = clientData

            player.displayName = loginData.displayName

            if (server.serverProperties!!.secureMode && player.loginData!!.jwtVerify) {
                player.state = PlayerState.PreLogined

                initSecure(player, server)
            } else {
                playStatusPacket.status = PlayStatusPacket.LOGIN_SUCCESS
                player.sendPacket(playStatusPacket)

                player.state = PlayerState.Logined

                server.logger.info(I18n["katana.server.player.login", player.displayName, player.address])

                val resourcePacksInfoPacket = ResourcePacksInfoPacket()
                resourcePacksInfoPacket.resourcePackEntries.addAll(server.resourcePackManager.getResourcePacks())
                resourcePacksInfoPacket.mustAccept = server.serverProperties!!.forceResource
                player.sendPacket(resourcePacksInfoPacket)
            }
        }
    }

    private fun initSecure(player: ActorPlayer, server: IServer) {
        val remotePublicKey = player.loginData!!.publicKey as ECPublicKey
        val gen = ECKeyPairGenerator()
        val osName = System.getProperty("os.name").toLowerCase()
        val secureRandom: SecureRandom
        if (osName.contains("windows")) {
            secureRandom = SecureRandom.getInstance("Windows-PRNG")
            gen.initialize(remotePublicKey.params, secureRandom)
        } else {
            secureRandom = SecureRandom.getInstance("NativePRNG")
            gen.initialize(remotePublicKey.params, secureRandom)
        }
        player.keyPair = gen.generateKeyPair()

        val ecPublicKey = player.keyPair!!.public as ECPublicKey
        val ecPrivateKey = player.keyPair!!.private as ECPrivateKey

        val keyAgreement = KeyAgreement.getInstance("ECDH")
        keyAgreement.init(ecPrivateKey)
        keyAgreement.doPhase(remotePublicKey, true)
        val sharedSecret = keyAgreement.generateSecret()

        var secretPrepend = ByteArray(16)
        secureRandom.nextBytes(secretPrepend)
        secretPrepend = Base64.getUrlEncoder().encode(secretPrepend)

        val messageDigest = MessageDigest.getInstance("SHA-256")
        messageDigest.update(secretPrepend + sharedSecret)
        val sharedKey = messageDigest.digest()

        val iv = ByteArray(16)
        System.arraycopy(sharedKey, 0, iv, 0, 16)
        player.sharedKey = sharedKey

        val secretKey = SecretKeySpec(player.sharedKey, "AES")
        val ivParameterSpec = IvParameterSpec(iv)

        player.decrypt = Cipher.getInstance("AES/CFB8/NoPadding")
        player.decrypt!!.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)

        player.encrypt = Cipher.getInstance("AES/CFB8/NoPadding")
        player.encrypt!!.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)

        val payload = JWTClaimsSet.Builder()
            .claim("salt", String(Base64.getUrlEncoder().encode(secretPrepend)))
            .build()

        val jwk = ECKey.Builder(Curve.P_384, ecPublicKey)
            .privateKey(ecPrivateKey)
            .build()

        val header = JWSHeader.Builder(JWSAlgorithm.ES384)
            .keyID(jwk.keyID)
            .x509CertURL(URI.create(Base64.getEncoder().encodeToString(ecPublicKey.encoded)))
            .type(JOSEObjectType.JWT)
            .build()

        val signer = ECDSASigner(jwk)
        val signedJWT = SignedJWT(header, payload)
        signedJWT.sign(signer)

        server.logger.info(I18n["katana.server.network.encryptStarting", player.address])

        val handshakePacket = ServerToClientHandshakePacket()
        handshakePacket.token = signedJWT.serialize()
        player.sendPacket(handshakePacket)

        player.isEncrypted = true
    }
}