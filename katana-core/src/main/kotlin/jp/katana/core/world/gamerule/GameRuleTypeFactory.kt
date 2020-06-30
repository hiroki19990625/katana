package jp.katana.core.world.gamerule

import jp.katana.factory.SimpleFactory

class GameRuleTypeFactory : SimpleFactory<Byte, (String) -> IGameRule<*>>() {
    init {
        this += { name -> BooleanGameRule(name, false) }
        this += { name -> IntGameRule(name, 0) }
        this += { name -> FloatGameRule(name, 0f) }
    }

    override fun plusAssign(value: (String) -> IGameRule<*>) {
        val v = value("")
        if (!map.containsKey(v.type))
            map[v.type] = value
    }

    override fun get(name: Byte): ((String) -> IGameRule<*>)? {
        if (map.containsKey(name)) {
            return map[name]
        }

        return null
    }
}