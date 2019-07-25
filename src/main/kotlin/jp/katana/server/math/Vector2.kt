package jp.katana.server.math

data class Vector2(var x: Double, var y: Double) {
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
}
