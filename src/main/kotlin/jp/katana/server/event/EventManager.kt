package jp.katana.server.event

import jp.katana.core.event.EventHandler
import jp.katana.core.event.IEvent
import jp.katana.core.event.IEventManager
import jp.katana.server.event.player.PlayerCreateEvent
import jp.katana.server.event.server.ServerStartEvent

class EventManager : IEventManager {
    private val events: HashMap<Class<*>, EventHandler<*>> = HashMap()

    init {
        register(EventHandler<PlayerCreateEvent>())

        register(EventHandler<ServerStartEvent>())
    }

    override fun <T : IEvent> register(handler: EventHandler<T>) {
        if (events.containsKey(handler.javaClass)) {
            events[handler.javaClass] = handler
        }
    }

    override fun <T : EventHandler<V>, V> invoke(handler: Class<T>, value: V) {
        if (events.containsKey(handler.javaClass)) {
            events[handler.javaClass]!!(value)
        }
    }
}