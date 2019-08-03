package jp.katana.server.world.gamerule

import jp.katana.core.world.gamerule.IGameRule
import jp.katana.core.world.gamerule.IGameRules

@Suppress("UNCHECKED_CAST")
class GameRules : IGameRules {
    private val map = mutableMapOf<String, IGameRule<*>>()

    override fun put(rule: IGameRule<*>) {
        map[rule.name] = rule
    }

    override fun <T : IGameRule<*>> get(name: String): T {
        return map[name] as T
    }

    override fun getAll(): Array<IGameRule<*>> {
        return map.values.toTypedArray()
    }

    override fun remove(name: String) {
        map.remove(name)
    }

    override fun removeAll() {
        map.clear()
    }

    override fun size(): Int {
        return map.size
    }

    override fun contains(name: String): Boolean {
        return map.contains(name)
    }

    override fun getRuleType(name: String, type: Byte): IGameRule<*> {
        return IGameRules.factory[type]!!(name)
    }
}