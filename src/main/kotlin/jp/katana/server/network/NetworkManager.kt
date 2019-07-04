package jp.katana.server.network

import com.whirvis.jraknet.identifier.MinecraftIdentifier
import com.whirvis.jraknet.server.RakNetServer
import jp.katana.core.entity.IPlayer
import jp.katana.core.network.INetworkManager
import jp.katana.server.Server
import java.net.InetSocketAddress

class NetworkManager(private val server: Server) : INetworkManager {
    private val raknetServer: RakNetServer = RakNetServer(server.serverPort, server.maxPlayer)

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
        raknetServer.addListener(ServerListener(this))
        raknetServer.start()
    }

    override fun shutdown() {
        raknetServer.shutdown()
    }

    override fun addPlayer(address: InetSocketAddress, player: IPlayer) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removePlayer(address: InetSocketAddress) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayer(address: InetSocketAddress) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        val identifier = raknetServer.identifier
        if (identifier is MinecraftIdentifier) {
            identifier.onlinePlayerCount = raknetServer.sessionCount
        }
    }

    fun updateOnlinePlayerCount(count: Int) {
        val identifier = raknetServer.identifier
        if (identifier is MinecraftIdentifier) {
            identifier.onlinePlayerCount = count
        }
    }
}
