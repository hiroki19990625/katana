package jp.katana.server.factory

import jp.katana.core.factory.IFactory
import jp.katana.core.factory.IFactoryManager
import org.apache.logging.log4j.LogManager

class FactoryManager : IFactoryManager {
    private val factories: HashMap<Class<*>, IFactory<*, *>> = HashMap()

    init {
        register(CommandFactory())
        register(PacketFactory())
    }

    override fun <K, V> register(handler: IFactory<K, V>) {
        if (!factories.containsKey(handler.javaClass)) factories[handler.javaClass] = handler
        LogManager.getLogger().info(handler.javaClass)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <F : IFactory<*, *>> get(clazz: Class<F>): F? {
        if (factories.containsKey(clazz)) {
            return factories[clazz] as F
        }

        return null
    }
}