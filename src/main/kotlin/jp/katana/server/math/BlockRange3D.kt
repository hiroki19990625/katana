package jp.katana.server.math

import kotlin.math.max
import kotlin.math.min

data class BlockRange3D(val pos1: Vector3Int, val pos2: Vector3Int) {
    inline infix fun foreach(f: (Vector3Int) -> Unit) {
        val maxX = max(pos1.x, pos2.x)
        val maxY = max(pos1.y, pos2.y)
        val maxZ = max(pos1.z, pos2.z)
        val minX = min(pos1.x, pos2.x)
        val minY = min(pos1.y, pos2.y)
        val minZ = max(pos1.z, pos2.z)

        for (i in minX..maxX) {
            for (j in minY..maxY) {
                for (k in minZ..maxZ) {
                    f(Vector3Int(i, j, k))
                }
            }
        }
    }
}