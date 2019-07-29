package jp.katana.server.utils

import com.whirvis.jraknet.Packet
import jp.katana.core.data.ISkin
import jp.katana.server.data.Skin
import jp.katana.server.math.Vector3
import jp.katana.server.math.Vector3Int
import java.nio.charset.Charset
import kotlin.experimental.and

/**
 * バイナリのストリームを提供します。
 */
open class BinaryStream : Packet() {
    fun readVarString(): String {
        return String(read(readUnsignedVarInt()))
    }


    fun writeVarString(s: String): Packet {
        val array: ByteArray = s.toByteArray(Charset.forName("utf8"))
        writeUnsignedVarInt(array.size)
        write(*array)

        return this
    }

    fun writeVarInt(v: Int) {
        var value = v
        value = value shl 32 shr 32
        writeUnsignedVarInt(value shl 1 xor (value shr 31))
    }

    fun writeUnsignedVarInt(value: Int) {
        var v = value
        val buf = ByteArray(5)
        v = v and -0x1

        for (i in 0..4) {
            if (v shr 7 != 0) {
                buf[i] = (v or 0x80).toByte()
            } else {
                buf[i] = (v and 0x7f).toByte()
                write(*buf.copyOf(i + 1))
                return
            }
            v = v shr 7 and (Integer.MAX_VALUE shr 6)
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

    fun writeVarLong(v: Long) {
        writeUnsignedVarLong(v shl 1 xor (v shr 63))
    }

    fun writeUnsignedVarLong(value: Long) {
        var v = value
        val buf = ByteArray(10)

        for (i in 0..9) {
            if (v.toLong() shr 7 != 0L) {
                buf[i] = (v or 0x80).toByte()
            } else {
                buf[i] = (v and 0x7f).toByte()
                write(*buf.copyOf(i + 1))
                return
            }
            v = v shr 7 and (Integer.MAX_VALUE shr 6).toLong()
        }
    }

    fun readVarLong(): Long {
        val raw = readUnsignedVarLong()
        val temp = raw shl 63 shr 63 xor raw shr 1
        return temp xor (raw and (1 shl 63))
    }

    fun readUnsignedVarLong(): Long {
        var value = 0L
        var i = 0
        while (i <= 63) {
            val b = readByte()
            value = value or ((b and 0x7f).toLong() shl i)
            if (b and 0x80.toByte() == 0.toByte()) {
                return value
            }
            i += 7
        }

        return value
    }

    fun readSkin(): ISkin {
        val skinId = readVarString()
        val skinData = String(read(readUnsignedVarInt()))
        val capeData = String(read(readUnsignedVarInt()))
        val geometryName = readVarString()
        val geometryData = readVarString()

        return Skin(capeData, skinData, geometryData, geometryName, skinId)
    }

    fun writeSkin(skin: ISkin) {
        writeVarString(skin.skinId)

        val skinData = skin.skinData.toByteArray()
        writeUnsignedVarInt(skinData.size)
        write(*skinData)

        val capeData = skin.capeData.toByteArray()
        writeUnsignedVarInt(capeData.size)
        write(*capeData)

        writeVarString(skin.skinGeometryName)
        writeVarString(skin.skinGeometry)
    }

    fun readBlockPosition(): Vector3Int {
        return Vector3Int(readVarInt(), readUnsignedVarInt(), readVarInt())
    }

    fun writeBlockPosition(pos: Vector3Int) {
        writeVarInt(pos.x)
        writeUnsignedVarInt(pos.y)
        writeVarInt(pos.z)
    }

    fun readUnsignedBlockPosition(): Vector3Int {
        return Vector3Int(readVarInt(), readVarInt(), readVarInt())
    }

    fun writeUnsignedBlockPosition(pos: Vector3Int) {
        writeVarInt(pos.x)
        writeVarInt(pos.y)
        writeVarInt(pos.z)
    }

    fun readVector3(): Vector3 {
        return Vector3(readFloatLE().toDouble(), readFloatLE().toDouble(), readFloatLE().toDouble())
    }

    fun writeVector3(pos: Vector3) {
        writeFloatLE(pos.x)
        writeFloatLE(pos.y)
        writeFloatLE(pos.z)
    }

    fun readEntityUniqueId(): Long {
        return readVarLong()
    }

    fun writeEntityUniqueId(id: Long) {
        writeVarLong(id)
    }

    fun readEntityRuntimeId(): Long {
        return readUnsignedVarLong()
    }

    fun writeEntityRuntimeId(id: Long) {
        writeUnsignedVarLong(id)
    }
}