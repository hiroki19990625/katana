package jp.katana.utils

import com.whirvis.jraknet.Packet
import jp.katana.core.data.ISkin
import jp.katana.core.world.gamerule.IGameRules
import jp.katana.math.Vector3
import jp.katana.math.Vector3Int
import jp.katana.server.data.Skin
import jp.katana.server.data.SkinImage
import java.math.BigInteger
import java.nio.charset.Charset
import java.util.*

/**
 * バイナリのストリームを提供します。
 */
open class BinaryStream {
    val stream: Packet = Packet()

    fun read(len: Int): ByteArray {
        return stream.read(len)
    }

    fun write(value: ByteArray) {
        stream.write(*value)
    }

    fun readBoolean(): Boolean {
        return stream.readBoolean()
    }

    fun writeBoolean(value: Boolean) {
        stream.writeBoolean(value)
    }

    fun readByte(): Byte {
        return stream.readByte()
    }

    fun writeByte(value: Byte) {
        stream.writeByte(value.toInt())
    }

    fun readUnsignedByte(): Short {
        return stream.readUnsignedByte()
    }

    fun writeUnsignedByte(value: Short) {
        stream.writeUnsignedByte(value.toInt())
    }

    fun readShort(): Short {
        return stream.readShort()
    }

    fun writeShort(value: Short) {
        stream.writeShort(value.toInt())
    }

    fun readShortLE(): Short {
        return stream.readShortLE()
    }

    fun writeShortLE(value: Short) {
        stream.writeShortLE(value.toInt())
    }

    fun readUnsignedShort(): Int {
        return stream.readUnsignedShort()
    }

    fun writeUnsignedShort(value: Int) {
        stream.writeUnsignedShort(value)
    }

    fun readUnsignedShortLE(): Int {
        return stream.readUnsignedShortLE()
    }

    fun writeUnsignedShortLE(value: Int) {
        stream.writeUnsignedShortLE(value)
    }

    fun readInt(): Int {
        return stream.readInt()
    }

    fun writeInt(value: Int) {
        stream.writeInt(value)
    }

    fun readIntLE(): Int {
        return stream.readIntLE()
    }

    fun writeIntLE(value: Int) {
        stream.writeIntLE(value)
    }

    fun readUnsignedInt(): Long {
        return stream.readUnsignedInt()
    }

    fun writeUnsignedInt(value: Long) {
        stream.writeUnsignedInt(value)
    }

    fun readUnsignedIntLE(): Long {
        return stream.readUnsignedIntLE()
    }

    fun writeUnsignedIntLE(value: Long) {
        stream.writeUnsignedIntLE(value)
    }

    fun readLong(): Long {
        return stream.readLong()
    }

    fun writeLong(value: Long) {
        stream.writeLong(value)
    }

    fun readLongLE(): Long {
        return stream.readLongLE()
    }

    fun writeLongLE(value: Long) {
        stream.writeLongLE(value)
    }

    fun readUnsignedLong(): BigInteger {
        return stream.readUnsignedLong()
    }

    fun writeUnsignedLong(value: BigInteger) {
        stream.writeUnsignedLong(value)
    }

    fun readUnsignedLongLE(): BigInteger {
        return stream.readUnsignedLongLE()
    }

    fun writeUnsignedLongLE(value: BigInteger) {
        stream.writeUnsignedLongLE(value)
    }

    fun readUtf8String(): String {
        return stream.readString()
    }

    fun writeUtf8String(value: String) {
        stream.writeString(value)
    }

    fun readUtf8StringLE(): String {
        return stream.readStringLE()
    }

    fun writeUtf8StringLE(value: String) {
        stream.writeStringLE(value)
    }

    fun readFloat(): Float {
        return stream.readFloat()
    }

    fun writeFloat(value: Float) {
        stream.writeFloat(value.toDouble())
    }

    fun readFloatLE(): Float {
        return stream.readFloatLE()
    }

    fun writeFloatLE(value: Float) {
        stream.writeFloatLE(value.toDouble())
    }

    fun readDouble(): Double {
        return stream.readDouble()
    }

    fun writeDouble(value: Double) {
        stream.writeDouble(value)
    }

    fun readDoubleLE(): Double {
        return stream.readDoubleLE()
    }

    fun writeDoubleLE(value: Double) {
        stream.writeDoubleLE(value)
    }

    fun readUUID(): UUID {
        return stream.readUUID()
    }

    fun writeUUID(value: UUID) {
        stream.writeUUID(value)
    }

    fun readVarString(): String {
        return String(read(readUnsignedVarInt()))
    }

    fun writeVarString(s: String) {
        val array: ByteArray = s.toByteArray(Charset.forName("utf8"))
        writeUnsignedVarInt(array.size)
        write(array)
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

    fun readGameRules(rules: IGameRules) {
        val len = readUnsignedVarInt()
        for (i in 0 until len) {
            val name = readVarString()
            val type = readByte()
            val rule = rules.getRuleType(name, type)
            rule.read(this)
            rules.put(rule)
        }
    }

    fun writeGameRules(rules: IGameRules) {
        writeUnsignedVarInt(rules.size())
        for (rule in rules.getAll()) {
            rule.write(this)
        }
    }

    fun readSkin(): ISkin {
        val skinId = readVarString()
        val resource = readVarString()
        val skinDataW = readIntLE()
        val skinDataH = readIntLE()
        val skinData = String(read(readUnsignedVarInt()))
        val capeDataW = readIntLE()
        val capeDataH = readIntLE()
        val capeData = String(read(readUnsignedVarInt()))
        readIntLE()
        val geometryData = readVarString()
        val animationData = readVarString()
        val premiumSkin = readBoolean()
        val personaSkin = readBoolean()
        val capeOnClassicSkin = readBoolean()
        val capeId = readVarString()

        return Skin(
            SkinImage(skinDataW, skinDataH, skinData),
            SkinImage(capeDataW, capeDataH, capeData),
            geometryData,
            animationData,
            resource,
            skinId,
            capeId,
            premiumSkin,
            personaSkin,
            capeOnClassicSkin
        )
    }

    fun writeSkin(skin: ISkin) {
        writeVarString(skin.skinId)
        writeVarString(skin.skinResourcePatch)

        val skinData = skin.skinData.data.toByteArray()
        writeIntLE(skin.skinData.width)
        writeIntLE(skin.skinData.height)
        writeUnsignedVarInt(skinData.size)
        write(skinData)

        val capeData = skin.capeData.data.toByteArray()
        writeIntLE(skin.capeData.width)
        writeIntLE(skin.capeData.height)
        writeUnsignedVarInt(capeData.size)
        write(capeData)

        writeIntLE(0)
        //TODO: Animation

        writeVarString(skin.skinGeometry)
        writeVarString(skin.skinAnimation)

        writeBoolean(skin.premiumSkin)
        writeBoolean(skin.personaSkin)
        writeBoolean(skin.capeOnClassicSkin)

        writeVarString(skin.capeId)
        writeVarString(skin.fullSkinId)
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
        writeFloatLE(pos.x.toFloat())
        writeFloatLE(pos.y.toFloat())
        writeFloatLE(pos.z.toFloat())
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

    fun readRemaining(): ByteArray {
        return read(stream.remaining())
    }

    fun setBuffer(buf: ByteArray) {
        stream.setBuffer(buf)
    }

    fun array(): ByteArray {
        return stream.array()
    }

    fun clear() {
        stream.clear()
    }

    fun close() {
        clear()
        stream.buffer().release()
    }
}