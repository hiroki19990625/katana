package jp.katana.server.math

import jp.katana.core.world.IWorld

data class Vector2(var x: Double, var y: Double) {
    companion object {
        val ZERO = Vector2(0.0, 0.0)
    }

    operator fun plus(a: Vector2): Vector2 {
        return Vector2(x + a.x, y + a.y)
    }

    operator fun plusAssign(a: Vector2) {
        x += a.x
        y += a.y
    }

    operator fun minus(a: Vector2): Vector2 {
        return Vector2(x - a.x, y - a.y)
    }

    operator fun minusAssign(a: Vector2) {
        x -= a.x
        y -= a.y
    }

    operator fun times(a: Double): Vector2 {
        return Vector2(x * a, y * a)
    }

    operator fun timesAssign(a: Double) {
        x *= a
        y *= a
    }

    operator fun div(a: Double): Vector2 {
        return Vector2(x / a, y / a)
    }

    operator fun divAssign(a: Double) {
        x /= a
        y /= a
    }

    operator fun rem(a: Double): Vector2 {
        return Vector2(x % a, y % a)
    }

    operator fun remAssign(a: Double) {
        x %= a
        y %= a
    }

    override fun equals(other: Any?): Boolean {
        return other is Vector2 && x == other.x && y == other.y
    }

    fun toVector2Int(): Vector2Int {
        return Vector2Int(x.toInt(), y.toInt())
    }

    fun toVector3IntXY(): Vector3Int {
        return Vector3Int(x.toInt(), y.toInt(), 0)
    }

    fun toVector3IntXZ(): Vector3Int {
        return Vector3Int(x.toInt(), 0, y.toInt())
    }

    fun toVector3XY(): Vector3 {
        return Vector3(x, y, 0.0)
    }

    fun toVector3XZ(): Vector3 {
        return Vector3(x, 0.0, y)
    }

    fun toPositionXY(world: IWorld): Position {
        return Position(world, Vector3(x, y, 0.0))
    }

    fun toPositionXZ(world: IWorld): Position {
        return Position(world, Vector3(x, 0.0, y))
    }

    fun toBlockPositionXY(world: IWorld): BlockPosition {
        return BlockPosition(world, Vector3(x, y, 0.0).toVector3Int())
    }

    fun toBlockPositionXZ(world: IWorld): BlockPosition {
        return BlockPosition(world, Vector3(x, 0.0, y).toVector3Int())
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}
