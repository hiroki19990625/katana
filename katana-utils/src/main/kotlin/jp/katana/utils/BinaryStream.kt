package jp.katana.utils

import com.whirvis.jraknet.Packet
import java.math.BigInteger
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
        return String(read(readUnsignedVarInt()), Charsets.UTF_8)
    }

    fun writeVarString(s: String) {
        val array: ByteArray = s.toByteArray(Charsets.UTF_8)
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