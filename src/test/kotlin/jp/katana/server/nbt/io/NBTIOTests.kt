package jp.katana.server.nbt.io

import jp.katana.server.nbt.Endian
import jp.katana.server.nbt.tag.CompoundTag
import jp.katana.server.nbt.tag.INamedTag
import jp.katana.server.nbt.tag.IntTag
import jp.katana.server.nbt.tag.ListTag
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException

class NBTIOTests {
    @Test
    fun readExTests() {
        val stream = this::class.java.classLoader.getResourceAsStream("raw.nbt") ?: throw IOException()
        val com = NBTIO.read(stream.readBytes(), Endian.Little)
    }

    @Test
    fun readWriteTagTests() {
        val com = CompoundTag("")
        val list = ListTag("test", INamedTag.INT)
        list.add(IntTag("", 1234))
        com.put(list)

        val buf = NBTIO.writeTag(com, Endian.Big, false)
        val ncom = NBTIO.readTag(buf, Endian.Big, false)
        Assertions.assertTrue(ncom.getList("test").getInt(0).value == 1234)
        Assertions.assertTrue(ncom.getList("test").getInt(0).name == "")
    }

    @Test
    fun readWriteZlibTagTests() {
        val com = CompoundTag("")
        val list = ListTag("test", INamedTag.INT)
        list.add(IntTag("", 1234))
        com.put(list)

        val buf = NBTIO.writeZlibTag(com, Endian.Big, false)
        val ncom = NBTIO.readZlibTag(buf, Endian.Big, false)
        Assertions.assertTrue(ncom.getList("test").getInt(0).value == 1234)
        Assertions.assertTrue(ncom.getList("test").getInt(0).name == "")
    }
}