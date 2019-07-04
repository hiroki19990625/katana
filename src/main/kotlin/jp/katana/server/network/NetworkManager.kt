package jp.katana.server.network

import com.whirvis.jraknet.identifier.MinecraftIdentifier
import com.whirvis.jraknet.server.RakNetServer
import com.whirvis.jraknet.session.RakNetClientSession
import jp.katana.core.entity.IPlayer
import jp.katana.core.network.INetworkManager
import jp.katana.server.Server
import java.net.InetSocketAddress

class NetworkManager(private val server: Server) : INetworkManager {
    private val raknetServer: RakNetServer = RakNetServer(server.serverPort, server.maxPlayer)
    private val players: HashMap<InetSocketAddress, IPlayer> = HashMap()
    private val sessions: HashMap<InetSocketAddress, RakNetClientSession> = HashMap()

    override fun start() {
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

    override fun shutdown() {
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
}
