package jp.katana.server.math

import jp.katana.core.world.IWorld

class Position(val world: IWorld, val pos: Vector3) {
    fun up(step: Double): Position {
        val n = pos + Vector3(0.0, step, 0.0)
        return Position(world, n)
    }

    fun down(step: Double): Position {
        val n = pos + Vector3(0.0, -step, 0.0)
        return Position(world, n)
    }

    fun left(step: Double): Position {
        val n = pos + Vector3(-step, 0.0, 0.0)
        return Position(world, n)
    }

    fun right(step: Double): Position {
        val n = pos + Vector3(step, 0.0, 0.0)
        return Position(world, n)
    }
}