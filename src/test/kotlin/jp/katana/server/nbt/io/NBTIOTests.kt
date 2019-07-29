package jp.katana.server.nbt.io

import jp.katana.server.nbt.Endian
import org.junit.jupiter.api.Test
import java.io.IOException

class NBTIOTests {
    @Test
    fun readExTests() {
        val stream = this::class.java.classLoader.getResourceAsStream("raw.nbt") ?: throw IOException()
        val com = NBTIO.read(stream.readBytes(), Endian.Little)
        println(com.toString())
    }
}