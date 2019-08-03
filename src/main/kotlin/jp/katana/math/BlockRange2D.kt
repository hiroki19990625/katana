package jp.katana.math

import kotlin.math.max
import kotlin.math.min

data class BlockRange2D(val pos1: Vector2Int, val pos2: Vector2Int) {
    inline infix fun foreach(f: (Vector2Int) -> Unit) {
        val maxX = max(pos1.x, pos2.x)
        val maxY = max(pos1.y, pos2.y)
        val minX = min(pos1.x, pos2.x)
        val minY = min(pos1.y, pos2.y)

        for (i in minX..maxX) {
            for (j in minY..maxY) {
                f(Vector2Int(i, j))
            }
        }
    }
}