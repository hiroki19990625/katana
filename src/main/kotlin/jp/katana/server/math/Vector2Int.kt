package jp.katana.server.math

data class Vector2Int(var x: Int, var y: Int) {
    operator fun plus(a: Vector2Int): Vector2Int {
        return Vector2Int(x + a.x, y + a.y)
    }

    operator fun plusAssign(a: Vector2Int) {
        x += a.x
        y += a.y
    }

    operator fun minus(a: Vector2Int): Vector2Int {
        return Vector2Int(x - a.x, y - a.y)
    }

    operator fun minusAssign(a: Vector2Int) {
        x -= a.x
        y -= a.y
    }

    operator fun times(a: Int): Vector2Int {
        return Vector2Int(x * a, y * a)
    }

    operator fun timesAssign(a: Int) {
        x *= a
        y *= a
    }

    operator fun div(a: Int): Vector2Int {
        return Vector2Int(x / a, y / a)
    }

    operator fun divAssign(a: Int) {
        x /= a
        y /= a
    }

    operator fun rem(a: Int): Vector2Int {
        return Vector2Int(x % a, y % a)
    }

    operator fun remAssign(a: Int) {
        x %= a
        y %= a
    }

    override fun equals(other: Any?): Boolean {
        return other is Vector2Int && x == other.x && y == other.y
    }
}