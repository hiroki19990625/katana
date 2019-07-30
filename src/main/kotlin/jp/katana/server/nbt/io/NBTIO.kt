package jp.katana.server.nbt.io

import jp.katana.server.nbt.Endian
import jp.katana.server.nbt.tag.CompoundTag
import jp.katana.server.nbt.tag.INamedTag
import java.io.IOException
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

        fun writeTag(tag: CompoundTag, endian: Endian = Endian.Little, isNetwork: Boolean = false): ByteArray {
            val stream = NBTStream(endian, isNetwork)
            stream.writeByte(tag.type)
            stream.writeString(tag.name)
            tag.write(stream)

            val buf = stream.getBuffer()
            stream.close()
            return buf
        }

        fun readTag(buffer: ByteArray, endian: Endian = Endian.Little, isNetwork: Boolean = false): CompoundTag {
            val stream = NBTStream(endian, isNetwork)
            stream.setBuffer(buffer)

            val type = stream.readByte()
            if (type == INamedTag.COMPOUND) {
                val tag = CompoundTag(stream.readString())
                tag.read(stream)

                stream.close()
                return tag
            }

            throw IOException("Not Compound")
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

        fun readZlibTag(buffer: ByteArray, endian: Endian = Endian.Little, isNetwork: Boolean = false): CompoundTag {
            val payload = ByteArray(1024 * 1024 * 64)
            val decompresser = Inflater()
            decompresser.setInput(buffer)

            val length = decompresser.inflate(payload)
            decompresser.end()
            return readTag(payload.copyOf(length), endian, isNetwork)
        }
    }
}