package jp.katana.server.event

import jp.katana.core.event.EventHandler
import jp.katana.core.event.IEvent
import jp.katana.core.event.IEventManager
import jp.katana.server.event.player.PlayerCreateEvent
import jp.katana.server.event.server.ServerStartEvent
import jp.katana.server.event.server.ServerStopEvent
import jp.katana.server.event.server.ServerUpdateTickEvent

class EventManager : IEventManager {
    private val events: HashMap<Class<*>, EventHandler<*>> = HashMap()

    init {
        register(EventHandler(), PlayerCreateEvent::class.java)

        register(EventHandler(), ServerStartEvent::class.java)
        register(EventHandler(), ServerStopEvent::class.java)
        register(EventHandler(), ServerUpdateTickEvent::class.java)
    }

    override fun <T : IEvent> register(handler: EventHandler<T>, clazz: Class<T>) {
        if (!events.containsKey(clazz)) events[clazz] = handler
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : IEvent> getEvent(clazz: Class<T>): EventHandler<T> {
        return events[clazz] as EventHandler<T>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : IEvent> invoke(value: V, clazz: Class<V>) {
        if (events.containsKey(clazz)) {
            val ev = events[clazz] as EventHandler<V>
            ev(value)
        }
    }
}