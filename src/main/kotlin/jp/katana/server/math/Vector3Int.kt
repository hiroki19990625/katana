package jp.katana.server.math

data class Vector3Int(var x: Int, var y: Int, var z: Int) {
    operator fun plus(a: Vector3Int): Vector3Int {
        return Vector3Int(x + a.x, y + a.y, z + a.z)
    }

    operator fun plusAssign(a: Vector3Int) {
        x += a.x
        y += a.y
        z += a.z
    }

    operator fun minus(a: Vector3Int): Vector3Int {
        return Vector3Int(x - a.x, y - a.y, z - a.z)
    }

    operator fun minusAssign(a: Vector3Int) {
        x -= a.x
        y -= a.y
        z -= a.z
    }

    operator fun times(a: Int): Vector3Int {
        return Vector3Int(x * a, y * a, z * a)
    }

    operator fun timesAssign(a: Int) {
        x *= a
        y *= a
        z += a
    }

    operator fun div(a: Int): Vector3Int {
        return Vector3Int(x / a, y / a, z / a)
    }

    operator fun divAssign(a: Int) {
        x /= a
        y /= a
        z /= a
    }

    operator fun rem(a: Int): Vector3Int {
        return Vector3Int(x % a, y % a, z % a)
    }

    operator fun remAssign(a: Int) {
        x %= a
        y %= a
        z %= a
    }

    override fun equals(other: Any?): Boolean {
        return other is Vector3Int && x == other.x && y == other.y && z == other.z
    }

    //TODO: Convert Func

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        return result
    }
}