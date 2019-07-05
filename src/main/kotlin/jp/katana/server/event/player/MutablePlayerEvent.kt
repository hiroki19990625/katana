package jp.katana.server.event.player

import jp.katana.core.entity.IPlayer
import jp.katana.core.event.IEvent

abstract class MutablePlayerEvent(var player: IPlayer?) : IEvent