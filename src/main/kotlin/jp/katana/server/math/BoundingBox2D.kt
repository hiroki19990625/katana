package jp.katana.server.math

import kotlin.math.max
import kotlin.math.min

data class BoundingBox2D(val pos: Vector2, val bound: Vector2) {
    fun contains(contain: Vector2): Boolean {
        val sum = pos + bound
        val maxX = max(sum.x, pos.x)
        val maxY = max(sum.y, pos.y)
        val minX = min(sum.x, pos.x)
        val minY = min(sum.y, pos.y)

        if (minX <= contain.x &&
            minY <= contain.y &&
            maxX >= contain.x &&
            maxY >= contain.y
        ) {
            return true
        }

        return false
    }

    fun contains(contain: BoundingBox2D): Boolean {
        val sum1 = pos + bound
        val maxX1 = max(sum1.x, pos.x)
        val maxY1 = max(sum1.y, pos.y)
        val minX1 = min(sum1.x, pos.x)
        val minY1 = min(sum1.y, pos.y)

        val sum2 = contain.pos + contain.bound;
        val maxX2 = max(sum2.x, contain.pos.x)
        val maxY2 = max(sum2.y, contain.pos.y)
        val minX2 = min(sum2.x, contain.pos.x)
        val minY2 = min(sum2.y, contain.pos.y)

        if (minX1 <= minX2 &&
            minY1 <= minY2 &&
            maxX1 >= minX1 &&
            maxY1 >= minY2 &&
            minX1 <= maxX2 &&
            minY1 <= maxY2 &&
            maxX1 >= maxX1 &&
            maxY1 >= maxY2
        ) {
            return true
        }

        return false
    }

    operator fun plus(a: BoundingBox2D): BoundingBox2D {
        return BoundingBox2D(pos + a.pos, bound + a.bound)
    }

    operator fun plusAssign(a: BoundingBox2D) {
        pos += a.pos
        bound += a.bound
    }

    operator fun minus(a: BoundingBox2D): BoundingBox2D {
        return BoundingBox2D(pos - a.pos, bound - a.bound)
    }

    operator fun minusAssign(a: BoundingBox2D) {
        pos -= a.pos
        bound -= a.bound
    }

    operator fun times(a: Double): BoundingBox2D {
        return BoundingBox2D(pos * a, bound * a)
    }

    operator fun timesAssign(a: Double) {
        pos *= a
        bound *= a
    }

    operator fun div(a: Double): BoundingBox2D {
        return BoundingBox2D(pos / a, bound / a)
    }

    operator fun divAssign(a: Double) {
        pos /= a
        bound /= a
    }

    operator fun rem(a: Double): BoundingBox2D {
        return BoundingBox2D(pos % a, bound % a)
    }

    operator fun remAssign(a: Double) {
        pos %= a
        bound %= a
    }

    override fun equals(other: Any?): Boolean {
        return other is BoundingBox2D && pos == other.pos && bound == other.bound
    }

    override fun hashCode(): Int {
        var result = pos.hashCode()
        result = 31 * result + bound.hashCode()
        return result
    }
}