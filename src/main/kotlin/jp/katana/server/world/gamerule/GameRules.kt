package jp.katana.server.world.gamerule

import jp.katana.core.world.gamerule.IGameRule
import jp.katana.core.world.gamerule.IGameRules

@Suppress("UNCHECKED_CAST")
class GameRules : IGameRules {
    private val map = mutableMapOf<String, IGameRule<*>>()

    val commandBlockOutput = get<BooleanGameRule>("commandblockoutput")
    val commandBlockSenabled = get<BooleanGameRule>("commandblocksenabled")
    val doDayLightCycle = get<BooleanGameRule>("dodaylightcycle")
    val doEntityDrops = get<BooleanGameRule>("doentitydrops")
    val doFireTick = get<BooleanGameRule>("dofiretick")
    val doImmediatereSpawn = get<BooleanGameRule>("doimmediaterespawn")
    val doInsomnia = get<BooleanGameRule>("doinsomnia")
    val doMobLoot = get<BooleanGameRule>("domobloot")
    val doMobSpawning = get<BooleanGameRule>("domobspawning")
    val doTileDrops = get<BooleanGameRule>("dotiledrops")
    val doWeatherCycle = get<BooleanGameRule>("doweathercycle")
    val drowningDamage = get<BooleanGameRule>("drowningdamage")
    val fallDamage = get<BooleanGameRule>("falldamage")
    val fireDamage = get<BooleanGameRule>("firedamage")
    val functionCommandLimit = get<IntGameRule>("functioncommandlimit")
    val keepInventory = get<BooleanGameRule>("keepinventory")
    val maxCommandChainLength = get<IntGameRule>("maxcommandchainlength")
    val mobGriefing = get<BooleanGameRule>("mobgriefing")
    val naturalRegeneration = get<BooleanGameRule>("naturalregeneration")
    val pvp = get<BooleanGameRule>("pvp")
    val randomTickSpeed = get<IntGameRule>("randomtickspeed")
    val sendCommandFeedback = get<BooleanGameRule>("sendcommandfeedback")
    val showCoordinates = get<BooleanGameRule>("showcoordinates")
    val showDeathMessages = get<BooleanGameRule>("showdeathmessages")
    val tntExplodes = get<BooleanGameRule>("tntexplodes")

    init {
        put(BooleanGameRule("commandblockoutput", true))
        put(BooleanGameRule("commandblocksenabled", true))
        put(BooleanGameRule("dodaylightcycle", true))
        put(BooleanGameRule("doentitydrops", true))
        put(BooleanGameRule("dofiretick", true))
        put(BooleanGameRule("doimmediaterespawn", false))
        put(BooleanGameRule("doinsomnia", true))
        put(BooleanGameRule("domobloot", true))
        put(BooleanGameRule("domobspawning", true))
        put(BooleanGameRule("dotiledrops", true))
        put(BooleanGameRule("doweathercycle", true))
        put(BooleanGameRule("drowningdamage", true))
        put(BooleanGameRule("falldamage", true))
        put(BooleanGameRule("firedamage", true))
        put(IntGameRule("functioncommandlimit", 10000))
        put(BooleanGameRule("keepinventory", true))
        put(IntGameRule("maxcommandchainlength", 65535))
        put(BooleanGameRule("mobgriefing", true))
        put(BooleanGameRule("naturalregeneration", true))
        put(BooleanGameRule("pvp", true))
        put(IntGameRule("randomtickspeed", 1))
        put(BooleanGameRule("sendcommandfeedback", true))
        put(BooleanGameRule("showcoordinates", false))
        put(BooleanGameRule("showdeathmessages", true))
        put(BooleanGameRule("tntexplodes", true))
    }

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