package jp.katana.client.factory

import jp.katana.client.Client
import jp.katana.factory.IFactory
import jp.katana.factory.IFactoryManager

class FactoryManager(client: Client) : IFactoryManager {
    override fun <K, V> register(handler: IFactory<K, V>) {
        TODO("Not yet implemented")
    }

    override fun <F : IFactory<*, *>> get(clazz: Class<F>): F? {
        TODO("Not yet implemented")
    }
}