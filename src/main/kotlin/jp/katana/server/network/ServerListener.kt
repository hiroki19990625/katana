package jp.katana.server.network

import com.whirvis.jraknet.RakNetPacket
import com.whirvis.jraknet.server.RakNetServerListener
import com.whirvis.jraknet.session.RakNetClientSession
import jp.katana.core.event.EventHandler
import jp.katana.i18n.I18n
import jp.katana.server.Server
import jp.katana.server.event.player.PlayerCreateEvent
import org.apache.logging.log4j.LogManager

class ServerListener(private val server: Server, private val networkManager: NetworkManager) : RakNetServerListener {
    private val logger = LogManager.getLogger()

    override fun onClientConnect(session: RakNetClientSession?) {
        if (session != null) {
            val address = session.address
            logger.info(I18n["katana.server.client.connection", address, session.maximumTransferUnit])

            val event = PlayerCreateEvent()
            server.eventManager.invoke(EventHandler.generateClass(), event)

            networkManager.addPlayer(address, event.player!!)
            networkManager.addSession(address, session)
            networkManager.updateOnlinePlayerCount()
        }
    }

    override fun onClientDisconnect(session: RakNetClientSession?, reason: String?) {
        if (session != null) {
            val address = session.address
            logger.info(I18n["katana.server.client.disConnection", address, reason ?: "none"])
            networkManager.removePlayer(address)
            networkManager.removeSession(address)
            networkManager.updateOnlinePlayerCount()
        }
    }

    override fun handleMessage(session: RakNetClientSession?, packet: RakNetPacket?, channel: Int) {
        logger.info(packet?.id)
    }

    override fun onSessionException(session: RakNetClientSession?, throwable: Throwable?) {
        logger.warn(throwable)
    }

    override fun onThreadException(throwable: Throwable?) {
        logger.warn(throwable)
    }
}