package jp.katana.utils

import org.junit.jupiter.api.Test

class VarIntTests {
    @Test
    fun tests() {
        val stram = BinaryStream()
        val bytes = ByteArray(3)
        bytes[0] = 0x80.toByte()
        bytes[1] = 0x80.toByte()
        bytes[2] = 0x40.toByte()

        stram.setBuffer(bytes)
        val value = VarInt.readUnsignedVarInt(stram);
        println(value)
        println(1048576)
    }
}