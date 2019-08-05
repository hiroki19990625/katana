package jp.katana.server.actor

import jp.katana.core.actor.IActorPlayer
import jp.katana.core.actor.PlayerState
import jp.katana.core.data.IClientData
import jp.katana.core.data.ILoginData
import jp.katana.core.network.IPacketHandler
import jp.katana.core.network.Reliability
import jp.katana.i18n.I18n
import jp.katana.server.Server
import jp.katana.server.network.PacketHandler
import jp.katana.server.network.packet.mcpe.DisconnectPacket
import jp.katana.server.network.packet.mcpe.MinecraftPacket
import org.apache.logging.log4j.LogManager
import java.net.InetSocketAddress
import java.security.KeyPair
import java.util.*
import javax.crypto.Cipher

class Player(override val address: InetSocketAddress, private val server: Server) : IActorPlayer {
    private val logger = LogManager.getLogger()
    override val packetHandler: IPacketHandler = PacketHandler(this, server)

    override var loginData: ILoginData? = null
        internal set
    override var clientData: IClientData? = null
        internal set
    override var keyPair: KeyPair? = null
        internal set

    override var sharedKey: ByteArray? = null
        internal set
    override var decrypt: Cipher? = null
        internal set
    override var encrypt: Cipher? = null
        internal set

    override var isEncrypted: Boolean = false
        internal set
    override var encryptCounter: Long = 0
    override var decryptCounter: Long = 0

    override var displayName: String = ""
        internal set

    override var state: PlayerState = PlayerState.Connected
        internal set

    override val uuid: UUID = UUID.randomUUID()

    override fun handlePacket(packet: MinecraftPacket) {
        packetHandler.handlePacket(packet)
    }

    override fun sendPacket(packet: MinecraftPacket, reliability: Reliability) {
        server.networkManager?.sendPacket(this, packet, reliability)
    }

    override fun disconnect(reason: String) {
        val disconnectPacket = DisconnectPacket()
        disconnectPacket.message = reason;
        sendPacket(disconnectPacket)
    }

    override fun onDisconnect(reason: String?) {
        if (state == PlayerState.Joined)
            logger.info(I18n["katana.server.player.leave", displayName])
    }
}