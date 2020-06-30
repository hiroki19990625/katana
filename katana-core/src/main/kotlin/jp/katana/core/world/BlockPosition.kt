package jp.katana.core.world

import jp.katana.math.Vector3Int

class BlockPosition(val world: IWorld, val pos: Vector3Int) {
    fun up(): BlockPosition {
        return up(1)
    }

    fun up(step: Int): BlockPosition {
        val n = pos + Vector3Int(0, step, 0)
        return BlockPosition(world, n)
    }

    fun down(): BlockPosition {
        return down(1)
    }

    fun down(step: Int): BlockPosition {
        val n = pos + Vector3Int(0, -step, 0)
        return BlockPosition(world, n)
    }

    fun left(): BlockPosition {
        return left(1)
    }

    fun left(step: Int): BlockPosition {
        val n = pos + Vector3Int(-step, 0, 0)
        return BlockPosition(world, n)
    }

    fun right(): BlockPosition {
        return right(1)
    }

    fun right(step: Int): BlockPosition {
        val n = pos + Vector3Int(step, 0, 0)
        return BlockPosition(world, n)
    }
}