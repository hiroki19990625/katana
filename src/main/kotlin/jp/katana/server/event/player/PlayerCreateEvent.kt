package jp.katana.server.event.player

import jp.katana.core.entity.IPlayer

class PlayerCreateEvent(player: IPlayer? = null) : MutablePlayerEvent(player)