package jp.katana.core.event

interface IEventManager {
    fun <T : IEvent> register(handler: EventHandler<T>)
    operator fun <V : IEvent> invoke(value: V)
}