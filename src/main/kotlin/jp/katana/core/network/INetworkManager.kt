package jp.katana.core.network

import com.whirvis.jraknet.server.RakNetServer
import jp.katana.server.Server

interface INetworkManager {
    val raknetServer: RakNetServer

    fun start()
    fun shutdown()
}