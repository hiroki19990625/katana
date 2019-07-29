package jp.katana.server.nbt.io

import jp.katana.server.nbt.Endian
import jp.katana.server.nbt.tag.CompoundTag

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
    }
}