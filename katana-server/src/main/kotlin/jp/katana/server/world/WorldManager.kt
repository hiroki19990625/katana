package jp.katana.server.world

import jp.katana.core.world.IWorld
import jp.katana.core.world.IWorldManager
import jp.katana.core.world.WorldType
import jp.katana.core.world.toWorldType
import jp.katana.server.Server
import java.io.File
import java.io.IOException
import java.util.*

class WorldManager(private val server: Server) : IWorldManager {
    private val worlds: MutableMap<UUID, IWorld> = mutableMapOf()

    override var defaultWorld: IWorld? = null
        private set

    override fun loadDefaultWorld(name: String) {
        val file = File("worlds")
        file.mkdir()

        val worldFile = File("worlds/$name/level.dat")
        defaultWorld = if (worldFile.exists()) {
            loadWorld(name)
        } else {
            createWorld(name, server.serverProperties!!.levelType.toWorldType())
        }
    }

    override fun loadWorld(name: String): IWorld {
        val guid = UUID.randomUUID()
        val world = World(name, server)
        world.loadData()
        worlds[guid] = world
        return world
    }

    override fun loadWorldFromFile(file: File, name: String): IWorld {
        val guid = UUID.randomUUID()
        val world = World(name, server)
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

    override fun createWorld(name: String): IWorld {
        return createWorld(name, WorldType.Default)
    }

    override fun createWorld(name: String, worldType: WorldType): IWorld {
        val worldFile = File("worlds/$name/level.dat")
        if (!worldFile.exists()) {
            worldFile.parentFile.mkdir()
            worldFile.createNewFile()

            val world = World(name, server)
            // TODO: Set WorldType
            return world
        }

        throw IOException()
    }
}