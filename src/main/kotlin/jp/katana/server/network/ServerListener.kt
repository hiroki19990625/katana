package jp.katana.server.network

import com.whirvis.jraknet.server.RakNetServerListener
import com.whirvis.jraknet.session.RakNetClientSession
import jp.katana.i18n.I18n
import org.apache.logging.log4j.LogManager

class ServerListener(private val networkManager: NetworkManager) : RakNetServerListener {
    private val logger = LogManager.getLogger()

    override fun onClientConnect(session: RakNetClientSession?) {
        if (session != null) {
            logger.info(I18n["katana.server.client.connection", session.address, session.maximumTransferUnit])
            networkManager.updateOnlinePlayerCount()
        }
    }

    override fun onClientDisconnect(session: RakNetClientSession?, reason: String?) {
        if (session != null) {
            logger.info(I18n["katana.server.client.disConnection", session.address, reason ?: "none"])
            networkManager.updateOnlinePlayerCount()
        }
    }

    override fun onSessionException(session: RakNetClientSession?, throwable: Throwable?) {
        logger.warn(throwable)
    }

    override fun onThreadException(throwable: Throwable?) {
        logger.warn(throwable)
    }
}