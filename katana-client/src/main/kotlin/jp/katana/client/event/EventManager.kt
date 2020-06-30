package jp.katana.client.event

import jp.katana.core.event.EventHandler
import jp.katana.core.event.IEvent
import jp.katana.core.event.IEventManager
import jp.katana.client.event.client.ClientStartEvent
import jp.katana.client.event.client.ClientStopEvent
import jp.katana.client.event.client.ClientUpdateTickEvent

class EventManager : IEventManager {
    private val events: HashMap<Class<*>, EventHandler<*>> = HashMap()

    init {
        register(EventHandler(), ClientStartEvent::class.java)
        register(EventHandler(), ClientStopEvent::class.java)
        register(EventHandler(), ClientUpdateTickEvent::class.java)
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