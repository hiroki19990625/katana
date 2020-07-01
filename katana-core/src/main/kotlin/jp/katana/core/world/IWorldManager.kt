package jp.katana.core.world

import java.io.File
import java.util.*

interface IWorldManager {
    val defaultWorld: IWorld?

    fun loadDefaultWorld(name: String)

    fun loadWorld(name: String): IWorld
    fun loadWorldFromFile(file: File, name: String): IWorld

    fun getWorld(uuid: UUID): IWorld
    fun getWorldByName(name: String): IWorld

    fun getWorlds(): Array<IWorld>

    fun unloadWorld(name: String)
    fun unloadWorld(uuid: UUID)
    fun unloadWorld(world: IWorld)

    fun createWorld(name: String): IWorld
    fun createWorld(name: String, worldType: WorldType): IWorld
}