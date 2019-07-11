package jp.katana.core.event

/**
 * イベントハンドラを提供します。
 * @param T : 発火するイベントデータ
 * @property list 登録されたイベント
 */
class EventHandler<T : IEvent> {
    private val list = mutableListOf<(T) -> Unit>()

    /**
     * イベントを追加で登録します。
     * @param a 登録する関数
     */
    operator fun plusAssign(a: (T) -> Unit) {
        list.add(a)
    }

    /**
     * イベントの登録を解除します。
     * @param a 削除する関数
     */
    operator fun minusAssign(a: (T) -> Unit) {
        list.remove(a)
    }

    /**
     * イベントを発火します。
     * @param value 発火するイベントデータ
     */
    operator fun invoke(value: T) {
        for (m in list)
            m(value)
    }

    /**
     * イベントの登録を全て解除します。
     */
    fun clear() {
        list.clear()
    }
}