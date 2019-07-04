package jp.katana.server.event.player

import jp.katana.core.entity.IPlayer
import jp.katana.core.event.IEvent

class PlayerCreateEvent(var player: IPlayer? = null) : IEvent