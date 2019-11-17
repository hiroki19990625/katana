package jp.katana.nbt.io

import jp.katana.nbt.Endian
import jp.katana.nbt.tag.CompoundTag
import jp.katana.nbt.tag.INamedTag
import java.io.ByteArrayOutputStream
import java.util.zip.DeflaterOutputStream
import java.util.zip.InflaterOutputStream

@Suppress("UNCHECKED_CAST")
class NBTIO {
    companion object {
        fun write(tag: CompoundTag, endian: Endian = Endian.Little, isNetwork: Boolean = false): ByteArray {
            val stream = NBTStream(endian, isNetwork)
            tag.write(stream)

            val buf = stream.getBuffer()
            stream.close()
            return buf
        }

        fun read(buffer: ByteArray, endian: Endian = Endian.Little, isNetwork: Boolean = false): CompoundTag {
            val stream = NBTStream(endian, isNetwork)
            stream.setBuffer(buffer)

            val tag = CompoundTag("")
            tag.read(stream)

            stream.close()
            return tag
        }

        fun writeTag(tag: INamedTag, endian: Endian = Endian.Little, isNetwork: Boolean = false): ByteArray {
            val stream = NBTStream(endian, isNetwork)
            stream.writeByte(tag.type)
            stream.writeString(tag.name)
            tag.write(stream)

            val buf = stream.getBuffer()
            stream.close()
            return buf
        }

        fun readTag(buffer: ByteArray, endian: Endian = Endian.Little, isNetwork: Boolean = false): INamedTag {
            val stream = NBTStream(endian, isNetwork)
            stream.setBuffer(buffer)

            val type = stream.readByte()
            val tag = INamedTag.getTag(type, stream.readString())
            tag.read(stream)

            stream.close()
            return tag
        }

        fun readTagRemaining(
            buffer: ByteArray,
            endian: Endian = Endian.Little,
            isNetwork: Boolean = false
        ): Pair<INamedTag, ByteArray> {
            val stream = NBTStream(endian, isNetwork)
            stream.setBuffer(buffer)

            val type = stream.readByte()
            val tag = INamedTag.getTag(type, stream.readString())
            tag.read(stream)

            val r = tag to stream.readRemaining()
            stream.close()

            return r
        }

        fun <T : INamedTag> readTagCast(
            buffer: ByteArray,
            endian: Endian = Endian.Little,
            isNetwork: Boolean = false
        ): T {
            return readTag(buffer, endian, isNetwork) as T
        }

        fun <T : INamedTag> readTagCastRemaining(
            buffer: ByteArray,
            endian: Endian = Endian.Little,
            isNetwork: Boolean = false
        ): Pair<T, ByteArray> {
            val pair = readTagRemaining(buffer, endian, isNetwork)
            return pair.first as T to pair.second
        }

        fun writeZlibTag(tag: CompoundTag, endian: Endian = Endian.Little, isNetwork: Boolean = false): ByteArray {
            val buffer = writeTag(tag, endian, isNetwork)

            val output = ByteArrayOutputStream()
            val compresser = DeflaterOutputStream(output)
            compresser.write(buffer)
            compresser.close()

            val buf = output.toByteArray()
            output.close()
            return buf
        }

        fun readZlibTag(buffer: ByteArray, endian: Endian = Endian.Little, isNetwork: Boolean = false): INamedTag {
            val payload = ByteArrayOutputStream()
            val decompresser = InflaterOutputStream(payload)
            decompresser.write(buffer)
            decompresser.close()

            val buf = payload.toByteArray()
            payload.close()
            return readTag(buf, endian, isNetwork)
        }
    }
}