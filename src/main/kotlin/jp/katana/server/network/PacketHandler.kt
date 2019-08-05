package jp.katana.server.network

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.ECDSASigner
import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.jwk.ECKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import jp.katana.core.actor.PlayerState
import jp.katana.core.network.IPacketHandler
import jp.katana.i18n.I18n
import jp.katana.server.Server
import jp.katana.server.actor.Player
import jp.katana.server.network.packet.mcpe.*
import jp.katana.server.world.gamerule.BooleanGameRule
import jp.katana.server.world.gamerule.GameRules
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
        when (packet // 0x01
            ) {
            is LoginPacket -> handleLoginPacket(packet)
            is PlayStatusPacket -> // 0x02
                handlePlayStatusPacket(packet)
            is ServerToClientHandshakePacket -> // 0x03
                handleServerToClientPacket(packet)
            is ClientToServerHandshakePacket -> // 0x04
                handleClientToServerPacket(packet)
            is DisconnectPacket -> // 0x05
                handleDisconnectPacket(packet)
            is ResourcePacksInfoPacket -> // 0x06
                handleResourcePacksInfoPacket(packet)
            is ResourcePackStackPacket -> // 0x07
                handleResourcePackStackPacket(packet)
            is ResourcePackClientResponsePacket -> // 0x08
                handleResourcePackClientResponsePacket(packet)
            is RequestChunkRadiusPacket -> // 0x45
                handleRequestChunkRadiusPacket(packet)
            is ChunkRadiusUpdatedPacket -> // 0x46
                handleChunkRadiusUpdatedPacket(packet)
            is ResourcePackDataInfoPacket -> // 0x52
                handleResourcePackDataInfoPacket(packet)
            is ResourcePackChunkDataPacket -> // 0x53
                handleResourcePackChunkDataPacket(packet)
            is ResourcePackChunkRequestPacket -> // 0x54
                handleResourcePackChunkRequestPacket(packet)
            is SetLocalPlayerAsInitializedPacket -> // 0x71
                handleSetLocalPlayerAsInitializedPacket(packet)
            is AvailableActorIdentifiersPacket -> // 0x77
                handleAvailableActorIdentifiersPacket(packet)
            is BiomeDefinitionListPacket -> // 0x7a
                handleBiomeDefinitionListPacket(packet)
        }
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

        player.displayName = loginPacket.loginData.displayName

        if (server.serverProperties!!.secureMode && player.loginData!!.jwtVerify) {
            player.state = PlayerState.PreLogined

            initSecure()
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

    override fun handlePlayStatusPacket(playStatusPacket: PlayStatusPacket) {
        // No cause
    }

    override fun handleServerToClientPacket(serverToClientHandshakePacket: ServerToClientHandshakePacket) {
        // No cause
    }

    override fun handleClientToServerPacket(clientToServerHandshakePacket: ClientToServerHandshakePacket) {
        server.logger.info(I18n["katana.server.network.encryptStarted", player.address])

        val playStatusPacket = PlayStatusPacket()
        playStatusPacket.status = PlayStatusPacket.LOGIN_SUCCESS
        player.sendPacket(playStatusPacket)

        player.state = PlayerState.Logined

        server.logger.info(I18n["katana.server.player.login", player.displayName, player.address])

        val resourcePacksInfoPacket = ResourcePacksInfoPacket()
        resourcePacksInfoPacket.resourcePackEntries.addAll(server.resourcePackManager.getResourcePacks())
        resourcePacksInfoPacket.mustAccept = server.serverProperties!!.forceResource
        player.sendPacket(resourcePacksInfoPacket)
    }

    override fun handleDisconnectPacket(disconnectPacket: DisconnectPacket) {
        // No cause
    }

    override fun handleResourcePacksInfoPacket(resourcePacksInfoPacket: ResourcePacksInfoPacket) {
        // No cause
    }

    override fun handleResourcePackStackPacket(resourcePackStackPacket: ResourcePackStackPacket) {
        // No cause
    }

    override fun handleResourcePackClientResponsePacket(resourcePackClientResponsePacket: ResourcePackClientResponsePacket) {
        when (resourcePackClientResponsePacket.status) {
            ResourcePackClientResponsePacket.STATUS_REFUSED -> {
                player.disconnect("disconnectionScreen.resourcePack")
            }

            ResourcePackClientResponsePacket.STATUS_SEND_PACKS -> {
                for (entry in resourcePackClientResponsePacket.packEntries) {
                    val pack = server.resourcePackManager.getResourcePack(entry.uuid.toString())
                    if (pack == null) {
                        player.disconnect("disconnectionScreen.resourcePack")
                        return
                    }

                    val resourcePackDataInfoPacket = ResourcePackDataInfoPacket()
                    resourcePackDataInfoPacket.packId = pack.packId
                    resourcePackDataInfoPacket.maxChunkSize = ResourcePackDataInfoPacket.KB_256
                    resourcePackDataInfoPacket.chunkCount =
                        (pack.packSize / resourcePackDataInfoPacket.maxChunkSize).toInt()
                    resourcePackDataInfoPacket.packSize = pack.packSize
                    resourcePackDataInfoPacket.hash = pack.hash

                    player.sendPacket(resourcePackDataInfoPacket)
                }
            }

            ResourcePackClientResponsePacket.STATUS_HAVE_ALL_PACKS -> {
                val resourcePackStackPacket = ResourcePackStackPacket()
                resourcePackStackPacket.mustAccept = server.serverProperties!!.forceResource
                resourcePackStackPacket.resourcePackStack.addAll(server.resourcePackManager.getResourcePacks())

                player.sendPacket(resourcePackStackPacket)
            }

            ResourcePackClientResponsePacket.STATUS_COMPLETED -> {
                startGame()
            }
        }
    }

    override fun handleRequestChunkRadiusPacket(requestChunkRadiusPacket: RequestChunkRadiusPacket) {
        val chunkRadiusUpdatedPacket = ChunkRadiusUpdatedPacket()
        chunkRadiusUpdatedPacket.radius = requestChunkRadiusPacket.radius
        player.sendPacket(chunkRadiusUpdatedPacket)
    }

    override fun handleChunkRadiusUpdatedPacket(chunkRadiusUpdatedPacket: ChunkRadiusUpdatedPacket) {
        // No cause
    }

    override fun handleResourcePackDataInfoPacket(resourcePackDataInfoPacket: ResourcePackDataInfoPacket) {
        // No cause
    }

    override fun handleResourcePackChunkDataPacket(resourcePackChunkDataPacket: ResourcePackChunkDataPacket) {
        // No cause
    }

    override fun handleResourcePackChunkRequestPacket(resourcePackChunkRequestPacket: ResourcePackChunkRequestPacket) {
        val pack = server.resourcePackManager.getResourcePack(resourcePackChunkRequestPacket.packId)
        if (pack == null) {
            player.disconnect("disconnectionScreen.resourcePack")
            return
        }

        val index = resourcePackChunkRequestPacket.chunkIndex
        val mb1 = ResourcePackDataInfoPacket.KB_256
        val offset = mb1 * index
        val resourcePackChunkDataPacket = ResourcePackChunkDataPacket()
        resourcePackChunkDataPacket.packId = pack.packId
        resourcePackChunkDataPacket.chunkIndex = index
        resourcePackChunkDataPacket.data = pack.getDataChunk(offset, mb1)
        resourcePackChunkDataPacket.progress = offset.toLong()

        player.sendPacket(resourcePackChunkDataPacket)
    }

    override fun handleSetLocalPlayerAsInitializedPacket(setLocalPlayerAsInitializedPacket: SetLocalPlayerAsInitializedPacket) {
        server.logger.info(I18n["katana.server.player.join", player.displayName])

        player.state = PlayerState.Joined
        // TODO: Send Chat

        // TODO: Event
    }

    override fun handleAvailableActorIdentifiersPacket(availableActorIdentifiersPacket: AvailableActorIdentifiersPacket) {
        // No cause
    }

    override fun handleBiomeDefinitionListPacket(biomeDefinitionListPacket: BiomeDefinitionListPacket) {
        // No cause
    }

    private fun initSecure() {
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

    private fun startGame() {
        val gameRules = GameRules()
        gameRules.put(BooleanGameRule("showcoordinates", true))
        val startGamePacket = StartGamePacket()
        startGamePacket.actorUniqueId = player.uuid.mostSignificantBits
        startGamePacket.actorRuntimeId = player.uuid.leastSignificantBits
        startGamePacket.blockDefinitions = server.defineBlocks
        startGamePacket.itemDefinitions = server.defineItems
        startGamePacket.gameRules = gameRules
        player.sendPacket(startGamePacket)

        val biomeDefinitionListPacket = BiomeDefinitionListPacket()
        biomeDefinitionListPacket.tag = server.defineBiomes.binary()
        player.sendPacket(biomeDefinitionListPacket)

        val availableActorIdentifiersPacket = AvailableActorIdentifiersPacket()
        availableActorIdentifiersPacket.tag = server.defineActors.binary()
        player.sendPacket(availableActorIdentifiersPacket)

        val playStatusPacket = PlayStatusPacket()
        playStatusPacket.status = PlayStatusPacket.PLAYER_SPAWN
        player.sendPacket(playStatusPacket)

        player.state = PlayerState.Spawned
    }
}