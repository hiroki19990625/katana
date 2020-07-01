package jp.katana.server.event.player

import jp.katana.core.actor.IActorPlayer

class PlayerCreateEvent(player: IActorPlayer? = null) : MutablePlayerEvent(player)