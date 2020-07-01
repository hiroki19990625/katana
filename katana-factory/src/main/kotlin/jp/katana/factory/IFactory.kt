package jp.katana.factory

/**
 * 拡張可能なデータファクトリを実装します。
 * @param K データファクトリのキー型
 * @param V データファクトリの値型
 */
interface IFactory<K, V> {
    operator fun get(name: K): V?
    operator fun set(name: K, value: V)
    operator fun plusAssign(value: V)
    operator fun minusAssign(name: K)
    fun keys(): List<K>
    fun values(): List<V>
    fun containsKey(key: K): Boolean
    fun containsValue(value: V): Boolean
    fun clear()
}