package jp.katana.server.block

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class BlockGrass : Block(BlockDefinitions.fromIdAllStates(GRASS)) {
    override fun onClick(server: IServer, player: IActorPlayer) {
        super.onClick(server, player)

        // TODO: Farm Process
    }
}