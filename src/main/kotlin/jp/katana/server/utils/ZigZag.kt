package jp.katana.server.utils


class ZigZag {
    companion object {
        fun encodeZigZag32(v: Int): Long {
            return (v shl 1 xor (v shr 31)).toLong()
        }

        fun decodeZigZag32(v: Long): Int {
            return (v shr 1).toInt() xor -(v and 1).toInt()
        }

        fun encodeZigZag64(v: Long): Long {
            return v shl 1 xor (v shr 63)
        }

        fun decodeZigZag64(v: Long): Long {
            return v.ushr(1) xor -(v and 1)
        }
    }
}