package jp.katana.server.world

import jp.katana.core.world.IWorld
import jp.katana.core.world.gamerule.IGameRules
import jp.katana.server.world.gamerule.GameRules

class World : IWorld {
    override val gameRules: IGameRules
        get() = GameRules()
}