package jp.katana.core.block

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.world.IWorld
import jp.katana.math.Vector3Int
import jp.katana.nbt.tag.CompoundTag
import jp.katana.nbt.tag.INamedTag

interface IBlock {
    val prefixName: String
    val name: String
    val fullName: String

    val id: Short
    val blockStates: List<Map<String, INamedTag>>?
    val nowState: Map<String, INamedTag>?

    val runtimeId: Int

    val world: IWorld?
    val position: Vector3Int

    val customTag: CompoundTag

    fun onClick(server: IServer, player: IActorPlayer)

    fun onPlace(server: IServer, player: IActorPlayer)
    fun onBreak(server: IServer, player: IActorPlayer)

    fun onTick(server: IServer, tick: Long)
}