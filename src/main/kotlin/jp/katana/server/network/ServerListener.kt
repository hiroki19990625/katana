package jp.katana.server.network

import com.whirvis.jraknet.RakNetPacket
import com.whirvis.jraknet.server.RakNetServerListener
import com.whirvis.jraknet.session.RakNetClientSession
import jp.katana.i18n.I18n
import jp.katana.server.Server
import jp.katana.server.entity.Player
import jp.katana.server.event.player.PlayerCreateEvent
import jp.katana.server.factory.PacketFactory
import jp.katana.server.network.packet.BatchPacket
import jp.katana.server.utils.BinaryStream
import org.apache.logging.log4j.LogManager

class ServerListener(private val server: Server, private val networkManager: NetworkManager) : RakNetServerListener {
    private val logger = LogManager.getLogger()
    private val factory = server.factoryManager.get(PacketFactory::class.java)!!

    override fun onClientConnect(session: RakNetClientSession?) {
        if (session != null) {
            val address = session.address
            logger.info(I18n["katana.server.client.connection", address, session.maximumTransferUnit])

            val event = PlayerCreateEvent()
            server.eventManager(event)

            if (event.player == null)
                event.player = Player(address)

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
        if (session != null) {
            val address = session.address
            val batch = BatchPacket()
            batch.setBuffer(packet?.array())
            batch.decode()

            var data = BinaryStream()
            data.setBuffer(batch.payload)
            val buf = data.read(data.readUnsignedVarInt())
            data = BinaryStream()
            data.setBuffer(buf)
            val id = data.readUnsignedVarInt()

            val pk = factory[id]
            if (pk != null) {
                pk.setBuffer(buf)
                pk.decode()

                networkManager.handlePacket(address, pk)
            }
        }
    }

    override fun onSessionException(session: RakNetClientSession?, throwable: Throwable?) {
        logger.warn("", throwable)
    }

    override fun onThreadException(throwable: Throwable?) {
        logger.warn("", throwable)
    }

    override fun onServerStart() {
        networkManager.updateState(true)
    }
}