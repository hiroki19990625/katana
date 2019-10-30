package jp.katana.server.world

import jp.katana.core.world.IWorld
import jp.katana.core.world.IWorldManager
import java.io.File
import java.util.*

class WorldManager : IWorldManager {
    private val worlds: MutableMap<UUID, IWorld> = mutableMapOf()

    override var defaultWorld: IWorld? = null
        internal set

    override fun loadWorld(name: String): IWorld {
        val guid = UUID.randomUUID()
        val world = World(name)
        world.loadData()
        worlds[guid] = world
        return world
    }

    override fun loadWorldFromFile(file: File, name: String): IWorld {
        val guid = UUID.randomUUID()
        val world = World(name)
        world.loadData(file)
        worlds[guid] = world
        return world
    }

    override fun getWorld(uuid: UUID): IWorld {
        return worlds[uuid]!!
    }

    override fun getWorldByName(name: String): IWorld {
        return worlds.values.first { w -> w.name == name }
    }

    override fun getWorlds(): Array<IWorld> {
        return worlds.values.toTypedArray()
    }

    override fun unloadWorld(name: String) {
        val world = worlds.entries.firstOrNull { w -> w.value.name == name }
        if (world != null)
            worlds.remove(world.key)
    }

    override fun unloadWorld(uuid: UUID) {
        if (worlds.containsKey(uuid))
            worlds.remove(uuid)
    }

    override fun unloadWorld(world: IWorld) {
        val w = worlds.entries.firstOrNull { w -> w.value.name == world.name }
        if (w != null)
            worlds.remove(w.key)
    }
}