package jp.katana.core.world

import jp.katana.math.Vector3

class Position(val world: IWorld, val pos: Vector3) {
    fun up(): Position {
        return up(1.0)
    }

    fun up(step: Double): Position {
        val n = pos + Vector3(0.0, step, 0.0)
        return Position(world, n)
    }

    fun down(): Position {
        return down(1.0)
    }

    fun down(step: Double): Position {
        val n = pos + Vector3(0.0, -step, 0.0)
        return Position(world, n)
    }

    fun left(): Position {
        return left(1.0)
    }

    fun left(step: Double): Position {
        val n = pos + Vector3(-step, 0.0, 0.0)
        return Position(world, n)
    }

    fun right(): Position {
        return right(1.0)
    }

    fun right(step: Double): Position {
        val n = pos + Vector3(step, 0.0, 0.0)
        return Position(world, n)
    }
}