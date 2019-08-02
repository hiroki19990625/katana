package jp.katana.core.world.gamerule

interface IGameRules {
    fun put(rule: IGameRule<*>)
    operator fun <T : IGameRule<*>> get(name: String): T
    fun getAll(): Array<IGameRule<*>>
    fun remove(name: String)
    fun removeAll()
    fun size(): Int
    fun contains(name: String): Boolean

    fun getRuleType(name: String, type: Byte): IGameRule<*>
}