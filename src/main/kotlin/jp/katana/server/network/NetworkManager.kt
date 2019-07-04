package jp.katana.server.network

import com.whirvis.jraknet.server.RakNetServer
import jp.katana.core.network.INetworkManager
import jp.katana.server.Server

class NetworkManager(private val server: Server) : INetworkManager {
    override val raknetServer: RakNetServer = RakNetServer(server.serverPort, server.maxPlayer)

    override fun start() {
        raknetServer.addSelfListener()
        raknetServer.start()
    }

    override fun shutdown() {
        raknetServer.shutdown()
    }
}
