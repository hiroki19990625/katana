package jp.katana.server.utils

import com.whirvis.jraknet.Packet
import jp.katana.core.data.ISkin
import jp.katana.server.data.Skin
import jp.katana.server.math.Vector3
import jp.katana.server.math.Vector3Int
import java.nio.charset.Charset

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

    fun readUnsignedVarIntByLong(): Long {
        return VarInt.readUnsignedVarInt(this)
    }

    fun readUnsignedVarInt(): Int {
        return VarInt.readUnsignedVarInt(this).toInt()
    }

    fun writeUnsignedVarInt(v: Long) {
        VarInt.writeUnsignedVarInt(this, v)
    }

    fun writeUnsignedVarInt(v: Int) {
        VarInt.writeUnsignedVarInt(this, v.toLong())
    }

    fun readVarInt(): Int {
        return VarInt.readVarInt(this)
    }

    fun writeVarInt(v: Int) {
        VarInt.writeVarInt(this, v)
    }

    fun readVarLong(): Long {
        return VarInt.readVarLong(this)
    }

    fun writeVarLong(v: Long) {
        VarInt.writeVarLong(this, v)
    }

    fun readUnsignedVarLong(): Long {
        return VarInt.readUnsignedVarLong(this)
    }

    fun writeUnsignedVarLong(v: Long) {
        VarInt.writeUnsignedVarLong(this, v)
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

    fun readActorUniqueId(): Long {
        return readVarLong()
    }

    fun writeActorUniqueId(id: Long) {
        writeVarLong(id)
    }

    fun readActorRuntimeId(): Long {
        return readUnsignedVarLong()
    }

    fun writeActorRuntimeId(id: Long) {
        writeUnsignedVarLong(id)
    }
}