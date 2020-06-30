package jp.katana.core.event

/**
 * イベントデータの管理を実装します。
 */
interface IEventManager {
    /**
     * イベントハンドラを登録します。
     * @param handler 登録するイベントハンドラ
     */
    fun <T : IEvent> register(handler: EventHandler<T>, clazz: Class<T>)

    /**
     * イベントハンドラを取得します。。
     * @param clazz Class<T>　イベントの型
     * @return EventHandler<T>　イベントハンドラ
     */
    fun <T : IEvent> getEvent(clazz: Class<T>): EventHandler<T>

    /**
     * イベントハンドラを発火します。
     * @param value 発火するイベントハンドラ
     */
    operator fun <V : IEvent> invoke(value: V, clazz: Class<V>)
}