package jp.katana.nbt.io

import jp.katana.nbt.Endian
import jp.katana.nbt.tag.CompoundTag
import jp.katana.nbt.tag.INamedTag
import java.util.zip.Deflater
import java.util.zip.Inflater

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

            val output = ByteArray(1024 * 1024 * 64)
            val compresser = Deflater()
            compresser.setInput(buffer)
            compresser.finish()
            val length = compresser.deflate(output)
            compresser.end()

            return output.copyOf(length)
        }

        fun readZlibTag(buffer: ByteArray, endian: Endian = Endian.Little, isNetwork: Boolean = false): INamedTag {
            val payload = ByteArray(1024 * 1024 * 64)
            val decompresser = Inflater()
            decompresser.setInput(buffer)

            val length = decompresser.inflate(payload)
            decompresser.end()
            return readTag(payload.copyOf(length), endian, isNetwork)
        }
    }
}