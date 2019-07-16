package jp.katana.server.network

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.ECDSASigner
import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.jwk.ECKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import jp.katana.core.network.IPacketHandler
import jp.katana.server.Server
import jp.katana.server.entity.Player
import jp.katana.server.network.packet.mcpe.LoginPacket
import jp.katana.server.network.packet.mcpe.MinecraftPacket
import jp.katana.server.network.packet.mcpe.PlayStatusPacket
import jp.katana.server.network.packet.mcpe.ServerToClientHandshakePacket
import sun.security.ec.ECKeyPairGenerator
import java.net.URI
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyAgreement
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class PacketHandler(private val player: Player, private val server: Server) : IPacketHandler {
    override fun handlePacket(packet: MinecraftPacket) {
        if (packet is LoginPacket)
            handleLoginPacket(packet)
        else if (packet is PlayStatusPacket)
            handlePlayStatusPacket(packet)
    }

    override fun handleLoginPacket(loginPacket: LoginPacket) {
        val protocol = loginPacket.protocol

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

        player.loginData = loginPacket.loginData
        player.clientData = loginPacket.clientData

        if (server.serverProperties!!.secureMode && player.loginData!!.jwtVerify) {
            initSecure()
        } else {
            playStatusPacket.status = PlayStatusPacket.LOGIN_SUCCESS
            player.sendPacket(playStatusPacket)
        }
    }

    override fun handlePlayStatusPacket(playStatusPacket: PlayStatusPacket) {
        // No cause
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleServerToClientPacket(serverToClientHandshakePacket: ServerToClientHandshakePacket) {
        // No cause
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleClientToServerPacket(clientHandshakePacket: ServerToClientHandshakePacket) {
        server.logger.info("encrypted!")
    }

    private fun initSecure() {
        val remotePublicKey = player.loginData!!.publicKey as ECPublicKey
        val gen = ECKeyPairGenerator()
        val osName = System.getProperty("os.name").toLowerCase()
        if (osName.contains("windows"))
            gen.initialize(remotePublicKey.params, SecureRandom.getInstance("Windows-PRNG"))
        else
            gen.initialize(remotePublicKey.params, SecureRandom.getInstance("NativePRNG"))
        player.keyPair = gen.generateKeyPair()

        val ecPublicKey = player.keyPair!!.public as ECPublicKey
        val ecPrivateKey = player.keyPair!!.private as ECPrivateKey

        val keyAgreement = KeyAgreement.getInstance("ECDH")
        keyAgreement.init(ecPrivateKey)
        keyAgreement.doPhase(remotePublicKey, true)
        val sharedSecret = keyAgreement.generateSecret()

        val messageDigest = MessageDigest.getInstance("SHA-256")
        messageDigest.update(sharedSecret)
        player.sharedKey = messageDigest.digest()

        val iv = ByteArray(16)
        player.sharedKey!!.copyInto(iv, 0, 16)

        val secretKey = SecretKeySpec(player.sharedKey, "AES")
        val ivParameterSpec = IvParameterSpec(iv)

        player.decrypt = Cipher.getInstance("AES/CFB8/NoPadding")
        player.decrypt!!.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)

        player.encrypt = Cipher.getInstance("AES/CFB8/NoPadding")
        player.encrypt!!.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)

        val payload = JWTClaimsSet.Builder()
            .claim("salt", "")
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

        player.isEncrypted = true

        val handshakePacket = ServerToClientHandshakePacket()
        handshakePacket.token = signedJWT.serialize()

        player.sendPacket(handshakePacket)
    }
}