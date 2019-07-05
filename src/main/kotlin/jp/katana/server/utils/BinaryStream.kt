package jp.katana.server.utils

import com.whirvis.jraknet.Packet
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*
import kotlin.experimental.and


open class BinaryStream : Packet() {
    override fun readString(): String {
        return String(read(readUnsignedVarInt()))
    }

    fun readFloatLE(): Float {
        val byteBuffer = ByteBuffer.allocate(4)
        byteBuffer.putFloat(readFloat())
        byteBuffer.flip()
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)

        return byteBuffer.float
    }

    fun writeFloatLE(value: Float) {
        val byteBuffer = ByteBuffer.allocate(4)
        byteBuffer.putFloat(value)
        byteBuffer.flip()

        write(byteBuffer.array())
    }

    fun writeVarInt(v: Int) {
        var v = v
        v = v shl 32 shr 32
        writeUnsignedVarInt(v shl 1 xor (v shr 31))
    }

    fun writeUnsignedVarInt(value: Int) {
        var value = value
        val buf = ByteArray(5)
        value = value and -0x1

        for (i in 0..4) {
            if (value shr 7 != 0) {
                buf[i] = (value or 0x80).toByte()
            } else {
                buf[i] = (value and 0x7f).toByte()
                write(buf.copyOf(i + 1))
                return
            }
            value = value shr 7 and (Integer.MAX_VALUE shr 6)
        }
    }

    fun readVarInt(): Int {
        val raw = readUnsignedVarInt()
        val temp = raw shl 63 shr 63 xor raw shr 1
        return temp xor (raw and (1 shl 63))
    }

    fun readUnsignedVarInt(): Int {
        var value = 0

        var i = 0
        while (i <= 63) {
            val b = readByte()
            value = value or ((b and 0x7f).toInt() shl i)
            if (b and 0x80.toByte() == 0.toByte()) {
                return value
            }
            i += 7
        }
        return value
    }

    fun writeVarLong(v: Int) {
        writeUnsignedVarLong(v shl 1 xor (v shr 63))
    }

    fun writeUnsignedVarLong(value: Int) {
        var value = value
        val buf = ByteArray(10)

        for (i in 0..9) {
            if (value shr 7 != 0) {
                buf[i] = (value or 0x80).toByte()
            } else {
                buf[i] = (value and 0x7f).toByte()
                write(Arrays.copyOf(buf, i + 1))
                return
            }
            value = value shr 7 and (Integer.MAX_VALUE shr 6)
        }
    }

    fun readVarLong(): Int {
        val raw = readUnsignedVarLong()
        val temp = raw shl 63 shr 63 xor raw shr 1
        return temp xor (raw and (1 shl 63))
    }

    fun readUnsignedVarLong(): Int {
        var value = 0
        var i = 0
        while (i <= 63) {
            val b = readByte()
            value = value or ((b and 0x7f).toInt() shl i)
            if (b and 0x80.toByte() == 0.toByte()) {
                return Integer.parseUnsignedInt(Integer.toUnsignedString(value))
            }
            i += 7
        }

        return value
    }
}