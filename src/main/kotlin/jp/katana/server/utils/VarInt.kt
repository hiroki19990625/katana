package jp.katana.server.utils

import jp.katana.server.utils.ZigZag.Companion.decodeZigZag32
import jp.katana.server.utils.ZigZag.Companion.decodeZigZag64
import jp.katana.server.utils.ZigZag.Companion.encodeZigZag32
import jp.katana.server.utils.ZigZag.Companion.encodeZigZag64
import java.io.IOException



class VarInt {
    companion object {
        private fun read(stream: BinaryStream, maxSize: Int): Long {
            var value: Long = 0
            var size = 0
            var b: Int = stream.readByte().toInt()
            while (b and 0x80 == 0x80) {
                value = value or ((b and 0x7F).toLong() shl size++ * 7)
                if (size >= maxSize) {
                    throw IllegalArgumentException("VarLong too big")
                }
                b = stream.readByte().toInt()
            }

            return value or ((b and 0x7F).toLong() shl size * 7)
        }

        private fun write(stream: BinaryStream, value: Long) {
            var value = value
            do {
                var temp = (value and 127).toByte()
                // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                value = value ushr 7
                if (value != 0L) {
                    temp = (temp.toInt() or 128).toByte()
                }
                stream.writeByte(temp.toInt())
            } while (value != 0L)
        }

        fun readVarInt(stream: BinaryStream): Int {
            return decodeZigZag32(readUnsignedVarInt(stream))
        }

        fun readUnsignedVarInt(stream: BinaryStream): Long {
            return read(stream, 5)
        }

        fun readVarLong(stream: BinaryStream): Long {
            return decodeZigZag64(readUnsignedVarLong(stream))
        }

        fun readUnsignedVarLong(stream: BinaryStream): Long {
            return read(stream, 10)
        }

        fun writeVarInt(stream: BinaryStream, value: Int) {
            writeUnsignedVarInt(stream, encodeZigZag32(value))
        }

        fun writeUnsignedVarInt(stream: BinaryStream, value: Long) {
            write(stream, value)
        }

        fun writeVarLong(stream: BinaryStream, value: Long) {
            writeUnsignedVarLong(stream, encodeZigZag64(value))
        }

        fun writeUnsignedVarLong(stream: BinaryStream, value: Long) {
            write(stream, value)
        }
    }
}