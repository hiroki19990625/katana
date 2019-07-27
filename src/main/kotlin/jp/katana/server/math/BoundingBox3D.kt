package jp.katana.server.math

import kotlin.math.max
import kotlin.math.min

data class BoundingBox3D(val pos: Vector3, val bound: Vector3) {
    fun contains(contain: Vector3): Boolean {
        val sum = pos + bound
        val maxX = max(sum.x, pos.x)
        val maxY = max(sum.y, pos.y)
        val maxZ = max(sum.z, pos.z)
        val minX = min(sum.x, pos.x)
        val minY = min(sum.y, pos.y)
        val minZ = min(sum.z, pos.z)

        if (minX <= contain.x &&
            minY <= contain.y &&
            minZ <= contain.z &&
            maxX >= contain.x &&
            maxY >= contain.y &&
            maxZ >= contain.z
        ) {
            return true
        }

        return false
    }

    fun contains(contain: BoundingBox3D): Boolean {
        val sum1 = pos + bound
        val maxX1 = max(sum1.x, pos.x)
        val maxY1 = max(sum1.y, pos.y)
        val maxZ1 = max(sum1.z, pos.z)
        val minX1 = min(sum1.x, pos.x)
        val minY1 = min(sum1.y, pos.y)
        val minZ1 = min(sum1.z, pos.z)

        val sum2 = contain.pos + contain.bound;
        val maxX2 = max(sum2.x, contain.pos.x)
        val maxY2 = max(sum2.y, contain.pos.y)
        val maxZ2 = max(sum2.z, contain.pos.z)
        val minX2 = min(sum2.x, contain.pos.x)
        val minY2 = min(sum2.y, contain.pos.y)
        val minZ2 = min(sum2.z, contain.pos.z)

        if (minX1 <= minX2 &&
            minY1 <= minY2 &&
            minZ1 <= minZ2 &&
            maxX1 >= minX1 &&
            maxY1 >= minY2 &&
            maxZ1 >= minZ2 &&
            minX1 <= maxX2 &&
            minY1 <= maxY2 &&
            minZ1 <= maxZ2 &&
            maxX1 >= maxX1 &&
            maxY1 >= maxY2 &&
            maxZ1 >= maxZ2
        ) {
            return true
        }

        return false
    }

    operator fun plus(a: BoundingBox3D): BoundingBox3D {
        return BoundingBox3D(pos + a.pos, bound + a.bound)
    }

    operator fun plusAssign(a: BoundingBox3D) {
        pos += a.pos
        bound += a.bound
    }

    operator fun minus(a: BoundingBox3D): BoundingBox3D {
        return BoundingBox3D(pos - a.pos, bound - a.bound)
    }

    operator fun minusAssign(a: BoundingBox3D) {
        pos -= a.pos
        bound -= a.bound
    }

    operator fun times(a: Double): BoundingBox3D {
        return BoundingBox3D(pos * a, bound * a)
    }

    operator fun timesAssign(a: Double) {
        pos *= a
        bound *= a
    }

    operator fun div(a: Double): BoundingBox3D {
        return BoundingBox3D(pos / a, bound / a)
    }

    operator fun divAssign(a: Double) {
        pos /= a
        bound /= a
    }

    operator fun rem(a: Double): BoundingBox3D {
        return BoundingBox3D(pos % a, bound % a)
    }

    operator fun remAssign(a: Double) {
        pos %= a
        bound %= a
    }

    infix fun `+p`(a: Vector3): BoundingBox3D {
        return BoundingBox3D(pos + a, bound)
    }

    infix fun `+=p`(a: Vector3) {
        pos += a
    }

    infix fun `-p`(a: Vector3): BoundingBox3D {
        return BoundingBox3D(pos - a, bound)
    }

    infix fun `-=p`(a: Vector3) {
        pos -= a
    }

    infix fun `*p`(a: Double): BoundingBox3D {
        return BoundingBox3D(pos * a, bound)
    }

    infix fun `*=p`(a: Double) {
        pos *= a
    }

    infix fun `divp`(a: Double): BoundingBox3D {
        return BoundingBox3D(pos / a, bound)
    }

    infix fun `div=p`(a: Double) {
        pos /= a
    }

    infix fun `%p`(a: Double): BoundingBox3D {
        return BoundingBox3D(pos % a, bound)
    }

    infix fun `%=p`(a: Double) {
        pos %= a
    }

    infix fun `+b`(a: Vector3): BoundingBox3D {
        return BoundingBox3D(pos, bound + a)
    }

    infix fun `+=b`(a: Vector3) {
        bound += a
    }

    infix fun `-b`(a: Vector3): BoundingBox3D {
        return BoundingBox3D(pos, bound - a)
    }

    infix fun `-=b`(a: Vector3) {
        bound -= a
    }

    infix fun `*b`(a: Double): BoundingBox3D {
        return BoundingBox3D(pos, bound * a)
    }

    infix fun `*=b`(a: Double) {
        bound *= a
    }

    infix fun `divb`(a: Double): BoundingBox3D {
        return BoundingBox3D(pos, bound / a)
    }

    infix fun `div=b`(a: Double) {
        bound /= a
    }

    infix fun `%b`(a: Double): BoundingBox3D {
        return BoundingBox3D(pos, bound % a)
    }

    infix fun `%=b`(a: Double) {
        bound %= a
    }

    fun relativeCenter(): Vector3 {
        return bound / 2.0
    }

    fun absoluteCenter(): Vector3 {
        return bound / 2.0 + pos
    }

    fun top(): Vector3 {
        return Vector3(bound.x / 2 + pos.x, pos.y, pos.z)
    }

    fun bottom(): Vector3 {
        return Vector3(bound.x / 2.0 + pos.x, pos.y + bound.y, pos.z)
    }

    fun left(): Vector3 {
        return Vector3(pos.x, bound.y / 2.0 + pos.y, pos.z)
    }

    fun right(): Vector3 {
        return Vector3(pos.x, bound.y / 2.0 + pos.y, pos.z + bound.z)
    }

    override fun equals(other: Any?): Boolean {
        return other is BoundingBox3D && pos == other.pos && bound == other.bound
    }

    override fun hashCode(): Int {
        var result = pos.hashCode()
        result = 31 * result + bound.hashCode()
        return result
    }
}