package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.actor.PlayerState
import jp.katana.i18n.I18n
import jp.katana.server.actor.ActorPlayer

class ClientToServerHandshakePacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.CLIENT_TO_SERVER_HANDSHAKE_PACKET

    override fun handle(player: IActorPlayer, server: IServer) {
        if (player is ActorPlayer) {
            server.logger.info(I18n["katana.server.network.encryptStarted", player.address])

            val playStatusPacket = PlayStatusPacket()
            playStatusPacket.status = PlayStatusPacket.LOGIN_SUCCESS
            player.sendPacket(playStatusPacket)

            player.state = PlayerState.Logined

            server.logger.info(I18n["katana.server.player.login", player.displayName, player.address])

            val resourcePacksInfoPacket = ResourcePacksInfoPacket()
            resourcePacksInfoPacket.resourcePackEntries.addAll(server.resourcePackManager.getResourcePacks())
            resourcePacksInfoPacket.mustAccept = server.serverProperties!!.forceResource
            player.sendPacket(resourcePacksInfoPacket)
        }
    }
}
