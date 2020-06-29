package jp.katana.server.actor

import jp.katana.core.actor.IActorPlayer
import jp.katana.core.actor.PlayerState
import jp.katana.core.actor.attribute.IActorAttributes
import jp.katana.core.actor.data.IActorDataManager
import jp.katana.core.data.IClientData
import jp.katana.core.data.ILoginData
import jp.katana.core.network.IPacketHandler
import jp.katana.core.network.Reliability
import jp.katana.core.world.IWorld
import jp.katana.i18n.I18n
import jp.katana.math.Vector2Int
import jp.katana.math.Vector3
import jp.katana.server.Server
import jp.katana.server.actor.attribute.ActorAttributes
import jp.katana.server.actor.data.*
import jp.katana.server.network.PacketHandler
import jp.katana.server.network.packet.mcpe.DisconnectPacket
import jp.katana.server.network.packet.mcpe.MinecraftPacket
import org.apache.logging.log4j.LogManager
import java.net.InetSocketAddress
import java.security.KeyPair
import java.util.*
import javax.crypto.Cipher

class ActorPlayer(override val address: InetSocketAddress, private val server: Server) : IActorPlayer {
    private val logger = LogManager.getLogger()

    private val loadedChunks: MutableMap<Vector2Int, Double> = mutableMapOf()

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

    override var chunkRadius: Int = 0
        internal set

    override var world: IWorld? = null
        internal set

    override var position: Vector3 = Vector3(0.0, 0.0, 0.0)
        internal set
    override var yaw: Double = 0.0
        internal set
    override var pitch: Double = 0.0
        internal set

    override val height: Double = 1.8
    override val width: Double = 0.6

    override val attributes: IActorAttributes = ActorAttributes(this, server)
    override val data: IActorDataManager = ActorDataManager(this, server)

    override val uuid: UUID = UUID.randomUUID()

    init {
        attributes.setAttribute(ActorAttributes.HUNGER)
        attributes.setAttribute(ActorAttributes.SATURATION)
        attributes.setAttribute(ActorAttributes.EXHAUSTION)
        attributes.setAttribute(ActorAttributes.EXPERIENCE)
        attributes.setAttribute(ActorAttributes.EXPERIENCE_LEVEL)
        attributes.setAttribute(ActorAttributes.MOVEMENT_SPEED)

        data.setData(ActorDataIds.DATA_FLAGS, LongActorData())
        data.setData(ActorDataIds.DATA_COLOR, ByteActorData())
        data.setData(ActorDataIds.DATA_AIR, ShortActorData(400))
        data.setData(ActorDataIds.DATA_MAX_AIR, ShortActorData(400))
        data.setData(ActorDataIds.DATA_NAME_TAG, StringActorData(displayName))
        data.setData(ActorDataIds.DATA_LEAD_HOLDER_EID, LongActorData(-1))
        data.setData(ActorDataIds.DATA_SCALE, FloatActorData(1f))
        data.setData(ActorDataIds.DATA_BOUNDING_BOX_WIDTH, FloatActorData(width.toFloat()))
        data.setData(ActorDataIds.DATA_BOUNDING_BOX_HEIGHT, FloatActorData(height.toFloat()))
        data.setData(ActorDataIds.DATA_HEALTH, IntActorData(20))

        data.setFlag(ActorDataIds.DATA_FLAGS, ActorFlags.DATA_FLAG_HAS_COLLISION)
        data.setFlag(ActorDataIds.DATA_FLAGS, ActorFlags.DATA_FLAG_GRAVITY)
        data.setFlag(ActorDataIds.DATA_FLAGS, ActorFlags.DATA_FLAG_BREATHING)
        data.setFlag(ActorDataIds.DATA_FLAGS, ActorFlags.DATA_FLAG_CAN_CLIMB)
    }

    override fun handlePacket(packet: MinecraftPacket) {
        packetHandler.handlePacket(packet)
    }

    override fun sendPacket(packet: MinecraftPacket, reliability: Reliability) {
        server.networkManager?.sendPacket(this, packet, reliability)
    }

    override fun disconnect(reason: String) {
        val disconnectPacket = DisconnectPacket()
        disconnectPacket.message = reason
        sendPacket(disconnectPacket)
    }

    override fun onDisconnect(reason: String?) {
        if (state == PlayerState.Joined)
            logger.info(I18n["katana.server.player.leave", displayName])

        world?.unregisterChunkLoader(this)
    }

    override fun getLoaderId(): Long {
        return uuid.leastSignificantBits
    }

    override fun getRadius(): Int {
        return chunkRadius
    }

    override fun getChunkPosition(): Vector2Int {
        return Vector2Int(position.x.toInt() shr 4, position.z.toInt() shr 4)
    }

    override fun getLoadedChunksMap(): MutableMap<Vector2Int, Double> {
        return loadedChunks
    }
}