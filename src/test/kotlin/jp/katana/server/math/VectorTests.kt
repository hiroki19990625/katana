package jp.katana.server.math

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class VectorTests {
    @Test
    fun tests() {
        val x = 10.0
        val y = 5.0
        val z = 20.0

        Assertions.assertTrue(Vector2(x, y) == Vector2(x, y))
        Assertions.assertTrue(Vector2(x, y) != Vector2(y, x))
        Assertions.assertTrue(Vector3(x, y, z) == Vector3(x, y, z))
        Assertions.assertTrue(Vector3(x, y, z) != Vector3(y, y, z))

        Assertions.assertTrue(Vector2(x, y) + Vector2(x, y) == Vector2(x + x, y + y))
        Assertions.assertTrue(Vector2(x, y) - Vector2(x, y) == Vector2(x - x, y - y))
        Assertions.assertTrue(Vector2(x, y) / 2.0 == Vector2(x / 2.0, y / 2.0))
        Assertions.assertTrue(Vector2(x, y) * 2.0 == Vector2(x * 2.0, y * 2.0))
        Assertions.assertTrue(Vector2(x, y) % 2.0 == Vector2(x % 2.0, y % 2.0))
    }
}