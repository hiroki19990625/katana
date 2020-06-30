package jp.katana.factory

abstract class SimpleFactory<K, V> : IFactory<K, V> {
    protected val map: HashMap<K, V> = HashMap()

    override operator fun get(name: K): V? {
        if (map.containsKey(name))
            return map[name]

        return null
    }

    override operator fun set(name: K, value: V) {
        map[name] = value
    }

    abstract override operator fun plusAssign(value: V)

    override operator fun minusAssign(name: K) {
        if (map.containsKey(name))
            map.remove(name)
    }

    override fun keys(): List<K> {
        return map.keys.toList()
    }

    override fun values(): List<V> {
        return map.values.toList()
    }

    override fun containsKey(key: K): Boolean {
        return map.containsKey(key)
    }

    override fun containsValue(value: V): Boolean {
        return map.containsValue(value)
    }

    override fun clear() {
        map.clear()
    }
}