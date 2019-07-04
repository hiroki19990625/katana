package jp.katana.core.event

interface IEventManager {
    fun <T : IEvent> register(handler: EventHandler<T>)
    operator fun <T : EventHandler<C>, C> invoke(handler: Class<T>, value: C)
}