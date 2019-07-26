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
}