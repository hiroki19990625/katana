package jp.katana.server.block

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.block.IBlock
import jp.katana.core.block.IBlockDefine
import jp.katana.core.world.IWorld
import jp.katana.math.Vector3Int
import jp.katana.nbt.tag.CompoundTag
import jp.katana.nbt.tag.INamedTag

open class Block(
    final override val fullName: String,
    override val id: Short,
    override val blockStates: List<Map<String, INamedTag>>,
    nowState: Map<String, INamedTag>
) :
    IBlock {
    companion object {
        const val AIR = 0
        const val STONE = 1
        const val GRASS = 2
    }

    override var nowState: Map<String, INamedTag> = nowState
        protected set

    override val prefixName: String = fullName.split(':')[0]
    override val name: String = fullName.split(':')[1]

    override var world: IWorld? = null
        internal set
    override var position: Vector3Int = Vector3Int(0, 0, 0)
        internal set

    override val customTag: CompoundTag = CompoundTag("")

    override val runtimeId: Int
        get() = BlockDefinitions.fromIdAndStates(id.toInt(), nowState).runtimeId

    constructor(define: IBlockDefine) : this(define.name, define.id, mutableListOf(define.states), define.states)
    constructor(defines: List<IBlockDefine>) : this(
        defines[0].name,
        defines[0].id,
        defines.map { e -> e.states },
        defines[0].states
    )

    override fun onClick(server: IServer, player: IActorPlayer) {
    }

    override fun onPlace(server: IServer, player: IActorPlayer) {
    }

    override fun onBreak(server: IServer, player: IActorPlayer) {
    }

    override fun onTick(server: IServer, tick: Long) {
    }
}