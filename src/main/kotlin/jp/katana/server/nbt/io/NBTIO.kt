package jp.katana.server.nbt.io

import jp.katana.server.nbt.Endian
import jp.katana.server.nbt.tag.CompoundTag
import jp.katana.server.nbt.tag.INamedTag
import java.io.IOException

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
    }
}