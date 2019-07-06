package jp.katana.core.event

class EventHandler<T : IEvent> {
    private val list = mutableListOf<(T) -> Unit>()

    operator fun plusAssign(a: (T) -> Unit) {
        list.add(a)
    }

    operator fun minusAssign(a: (T) -> Unit) {
        list.remove(a)
    }

    operator fun invoke(value: T) {
        for (m in list)
            m(value)
    }

    fun clear() {
        list.clear()
    }
}