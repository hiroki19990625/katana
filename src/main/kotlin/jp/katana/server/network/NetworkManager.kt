package jp.katana.server.network

import com.whirvis.jraknet.Packet
import com.whirvis.jraknet.identifier.MinecraftIdentifier
import com.whirvis.jraknet.server.RakNetServer
import com.whirvis.jraknet.session.RakNetClientSession
import jp.katana.core.entity.IPlayer
import jp.katana.core.network.INetworkManager
import jp.katana.core.network.Reliability
import jp.katana.i18n.I18n
import jp.katana.server.Server
import jp.katana.server.network.packet.BatchPacket
import jp.katana.server.network.packet.mcpe.MinecraftPacket
import jp.katana.server.utils.BinaryStream
import java.net.InetSocketAddress

class NetworkManager(private val server: Server) : INetworkManager {
    private val raknetServer: RakNetServer = RakNetServer(server.serverPort, server.maxPlayer)
    private val players: HashMap<InetSocketAddress, IPlayer> = HashMap()
    private val sessions: HashMap<InetSocketAddress, RakNetClientSession> = HashMap()

    private val networkThread: Thread = Thread { startNetworkThread() }
    private var networkStart = false

    override fun start() {
        if (networkThread.state == Thread.State.NEW)
            networkThread.start()
    }

    override fun shutdown() {
        while (!networkStart)
            Thread.sleep(1)
        raknetServer.shutdown()
    }

    override fun addPlayer(address: InetSocketAddress, player: IPlayer): Boolean {
        if (!players.containsKey(address)) {
            players[address] = player
            return true
        }

        return false
    }

    override fun removePlayer(address: InetSocketAddress): Boolean {
        if (players.containsKey(address)) {
            players.remove(address)
            return true
        }

        return false
    }

    override fun getPlayer(address: InetSocketAddress): IPlayer? {
        if (players.containsKey(address)) return players[address]

        return null
    }

    override fun getPlayers(): List<IPlayer> {
        return players.values.toList()
    }

    override fun sendPacket(player: IPlayer, packet: MinecraftPacket, reliability: Reliability) {
        val address = player.address
        if (sessions.containsKey(address)) {
            packet.encode()

            val binary = BinaryStream()
            val buf = packet.array()
            binary.writeUnsignedVarInt(buf.size)
            binary.write(buf)

            val batch = BatchPacket()
            batch.isEncrypt = player.isEncrypted
            batch.payload = binary.array()
            batch.encode()

            sessions[address]!!.sendMessage(
                com.whirvis.jraknet.protocol.Reliability.lookup(reliability.id.toInt()),
                packet.channel,
                Packet(batch.array())
            )
        }
    }

    override fun handlePacket(address: InetSocketAddress, packet: MinecraftPacket) {
        if (players.containsKey(address)) {
            players[address]!!.handlePacket(packet)
        }
    }

    fun addSession(address: InetSocketAddress, session: RakNetClientSession): Boolean {
        if (!sessions.containsKey(address)) {
            sessions[address] = session
            return true
        }

        return false
    }

    fun removeSession(address: InetSocketAddress): Boolean {
        if (sessions.containsKey(address)) {
            sessions.remove(address)
            return true
        }

        return false
    }

    fun updateIdentifier(motd: String, onlinePlayerCount: Int, maxPlayer: Int, subMotd: String) {
        val identifier = raknetServer.identifier
        if (identifier is MinecraftIdentifier) {
            identifier.serverName = motd
            identifier.onlinePlayerCount = onlinePlayerCount
            identifier.maxPlayerCount = maxPlayer
            identifier.worldName = subMotd
        }
    }

    fun updateOnlinePlayerCount() {
        updateOnlinePlayerCount(raknetServer.sessionCount)
    }

    fun updateOnlinePlayerCount(count: Int) {
        val identifier = raknetServer.identifier
        if (identifier is MinecraftIdentifier) {
            identifier.onlinePlayerCount = count
        }
    }

    internal fun updateState(f: Boolean) {
        networkStart = f
    }

    private fun startNetworkThread() {
        Thread.currentThread().name = I18n["katana.server.thread.networkThread"]

        raknetServer.identifier = MinecraftIdentifier(
            server.motd,
            server.protocolVersion,
            server.gameVersion,
            0,
            server.maxPlayer,
            raknetServer.globallyUniqueId,
            server.subMotd,
            ""
        )
        raknetServer.addSelfListener()
        raknetServer.addListener(ServerListener(server, this))
        raknetServer.start()
    }
}
