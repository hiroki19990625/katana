package jp.katana.server.math

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BoundingBoxTests {
    @Test
    fun containsTests() {
        val t1 = Vector3(1.0, 1.0, 1.0)
        val t2 = Vector3(5.5, 3.3, 2.2)
        val sam = Vector3(2.0, 2.0, 2.0)
        val sem = Vector3(99.0, 99.0, 99.0)
        val box3d = BoundingBox3D(t1, t1)
        val box3df = BoundingBox3D(sem, t1)
        val box2d = BoundingBox2D(t1.toVector2XY(), t1.toVector2XY())
        val box2df = BoundingBox2D(sem.toVector2XY(), t1.toVector2XY())
        val b3 = BoundingBox3D(t1, t2)
        val b2 = BoundingBox2D(t1.toVector2XY(), t2.toVector2XY())

        Assertions.assertTrue(b3.contains(sam))
        Assertions.assertFalse(b3.contains(sem))
        Assertions.assertTrue(b2.contains(sam.toVector2XY()))
        Assertions.assertFalse(b2.contains(sem.toVector2XY()))
        Assertions.assertTrue(b3.contains(box3d))
        Assertions.assertFalse(b3.contains(box3df))
        Assertions.assertTrue(b2.contains(box2d))
        Assertions.assertFalse(b2.contains(box2df))
    }
}