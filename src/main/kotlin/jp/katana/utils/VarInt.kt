package jp.katana.utils


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
            var vl = value
            do {
                var temp = (vl and 127).toByte()
                // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                vl = vl ushr 7
                if (vl != 0L) {
                    temp = (temp.toInt() or 128).toByte()
                }
                stream.writeByte(temp.toInt())
            } while (vl != 0L)
        }

        fun readVarInt(stream: BinaryStream): Int {
            return ZigZag.decodeZigZag32(readUnsignedVarInt(stream))
        }

        fun readUnsignedVarInt(stream: BinaryStream): Long {
            return read(stream, 5)
        }

        fun readVarLong(stream: BinaryStream): Long {
            return ZigZag.decodeZigZag64(readUnsignedVarLong(stream))
        }

        fun readUnsignedVarLong(stream: BinaryStream): Long {
            return read(stream, 10)
        }

        fun writeVarInt(stream: BinaryStream, value: Int) {
            writeUnsignedVarInt(stream, ZigZag.encodeZigZag32(value))
        }

        fun writeUnsignedVarInt(stream: BinaryStream, value: Long) {
            write(stream, value)
        }

        fun writeVarLong(stream: BinaryStream, value: Long) {
            writeUnsignedVarLong(stream, ZigZag.encodeZigZag64(value))
        }

        fun writeUnsignedVarLong(stream: BinaryStream, value: Long) {
            write(stream, value)
        }
    }
}