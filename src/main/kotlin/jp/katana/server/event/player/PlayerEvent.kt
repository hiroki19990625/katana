package jp.katana.server.event.player

import jp.katana.core.entity.IPlayer
import jp.katana.core.event.IEvent

abstract class PlayerEvent(val player: IPlayer) : IEvent