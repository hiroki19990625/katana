package jp.katana.server.math

import jp.katana.core.world.IWorld

class BlockPosition(val world: IWorld, val pos: Vector3Int) {
    fun up(step: Int): BlockPosition {
        val n = pos + Vector3Int(0, step, 0)
        return BlockPosition(world, n)
    }

    fun down(step: Int): BlockPosition {
        val n = pos + Vector3Int(0, -step, 0)
        return BlockPosition(world, n)
    }

    fun left(step: Int): BlockPosition {
        val n = pos + Vector3Int(-step, 0, 0)
        return BlockPosition(world, n)
    }

    fun right(step: Int): BlockPosition {
        val n = pos + Vector3Int(step, 0, 0)
        return BlockPosition(world, n)
    }
}