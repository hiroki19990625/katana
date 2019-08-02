package jp.katana.core.world

import jp.katana.core.world.gamerule.IGameRules

interface IWorld {
    val gameRules: IGameRules
}