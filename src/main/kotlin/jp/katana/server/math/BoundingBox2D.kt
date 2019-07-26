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

    fun addPosition(pos: Vector2): BoundingBox2D {
        return BoundingBox2D(this.pos + pos, this.bound)
    }

    fun addBound(bound: Vector2): BoundingBox2D {
        return BoundingBox2D(this.pos, this.bound + bound)
    }
}