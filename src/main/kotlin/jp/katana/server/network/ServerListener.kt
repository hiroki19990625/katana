package jp.katana.server.network

import com.whirvis.jraknet.RakNetPacket
import com.whirvis.jraknet.peer.RakNetClientPeer
import com.whirvis.jraknet.server.RakNetServer
import com.whirvis.jraknet.server.RakNetServerListener
import jp.katana.i18n.I18n
import jp.katana.server.Server
import jp.katana.server.entity.Player
import jp.katana.server.event.player.PlayerCreateEvent
import jp.katana.server.factory.PacketFactory
import jp.katana.server.network.packet.BatchPacket
import jp.katana.server.utils.BinaryStream
import org.apache.logging.log4j.LogManager
import java.net.InetSocketAddress

class ServerListener(private val server: Server, private val networkManager: NetworkManager) : RakNetServerListener {
    private val logger = LogManager.getLogger()
    private val factory = server.factoryManager.get(PacketFactory::class.java)!!

    override fun onLogin(rServer: RakNetServer?, peer: RakNetClientPeer?) {
        if (peer != null) {
            val address = peer.address
            logger.info(I18n["katana.server.client.connection", address, peer.maximumTransferUnit])

            val event = PlayerCreateEvent()
            server.eventManager(event)

            if (event.player == null)
                event.player = Player(address, server)

            networkManager.addPlayer(address, event.player!!)
            networkManager.addSession(address, peer)
            networkManager.updateOnlinePlayerCount()
        }
    }

    override fun onDisconnect(
        server: RakNetServer?,
        address: InetSocketAddress?,
        peer: RakNetClientPeer?,
        reason: String?
    ) {
        if (peer != null) {
            val address = peer.address
            logger.info(I18n["katana.server.client.disConnection", address, reason ?: "none"])
            networkManager.removePlayer(address)
            networkManager.removeSession(address)
            networkManager.updateOnlinePlayerCount()
        }
    }

    override fun handleMessage(server: RakNetServer?, peer: RakNetClientPeer?, packet: RakNetPacket?, channel: Int) {
        if (peer != null) {
            val address = peer.address
            val player = networkManager.getPlayer(address)!!
            val batch = BatchPacket()
            batch.isEncrypt = player.isEncrypted
            batch.decrypt = player.decrypt
            batch.encrypt = player.encrypt
            batch.decryptCounter = player.decryptCounter
            batch.encryptCounter = player.encryptCounter
            batch.sharedKey = player.sharedKey
            batch.setBuffer(packet?.array())
            batch.decode()

            player.decryptCounter++

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
            pk?.clear()
            data.clear()
            batch.clear()
        }
    }

    override fun onPeerException(server: RakNetServer?, peer: RakNetClientPeer?, throwable: Throwable?) {
        logger.warn("", throwable)
    }

    override fun onHandlerException(server: RakNetServer?, address: InetSocketAddress?, throwable: Throwable?) {
        logger.warn("", throwable)
    }

    override fun onStart(server: RakNetServer?) {
        networkManager.updateState(true)
    }
}