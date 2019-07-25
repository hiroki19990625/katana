package jp.katana.server.math

data class Vector3(var x: Double, var y: Double, var z: Double) {
    operator fun plus(a: Vector3): Vector3 {
        return Vector3(x + a.x, y + a.y, z + a.z)
    }

    operator fun plusAssign(a: Vector3) {
        x += a.x
        y += a.y
        z += a.z
    }

    operator fun minus(a: Vector3): Vector3 {
        return Vector3(x - a.x, y - a.y, z - a.z)
    }

    operator fun minusAssign(a: Vector3) {
        x -= a.x
        y -= a.y
        z -= a.z
    }

    operator fun times(a: Double): Vector3 {
        return Vector3(x * a, y * a, z * a)
    }

    operator fun timesAssign(a: Double) {
        x *= a
        y *= a
        z += a
    }

    operator fun div(a: Double): Vector3 {
        return Vector3(x / a, y / a, z / a)
    }

    operator fun divAssign(a: Double) {
        x /= a
        y /= a
        z /= a
    }

    operator fun rem(a: Double): Vector3 {
        return Vector3(x % a, y % a, z % a)
    }

    operator fun remAssign(a: Double) {
        x %= a
        y %= a
        z %= a
    }

    override fun equals(other: Any?): Boolean {
        return other is Vector3 && x == other.x && y == other.y && z == other.z
    }

    fun toVector3Int(): Vector3Int {
        return Vector3Int(x.toInt(), y.toInt(), z.toInt())
    }

    fun toVector2IntXY(): Vector2Int {
        return Vector2Int(x.toInt(), y.toInt())
    }

    fun toVector2IntXZ(): Vector2Int {
        return Vector2Int(x.toInt(), z.toInt())
    }

    fun toVector2XY(): Vector2 {
        return Vector2(x, y)
    }

    fun toVector2XZ(): Vector2 {
        return Vector2(x, z)
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }
}
