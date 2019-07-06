package jp.katana.core.factory

interface IFactoryManager {
    fun <K, V> register(handler: IFactory<K, V>)
    fun <F : IFactory<*, *>> get(clazz: Class<F>): F?
}