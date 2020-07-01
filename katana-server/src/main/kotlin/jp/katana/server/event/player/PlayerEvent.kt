package jp.katana.server.event.player

import jp.katana.core.actor.IActorPlayer
import jp.katana.core.event.IEvent

abstract class PlayerEvent(val player: IActorPlayer) : IEvent