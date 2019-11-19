package jp.katana.nbt.io

import jp.katana.nbt.Endian
import jp.katana.nbt.NBTFormatException
import jp.katana.nbt.tag.CompoundTag
import jp.katana.nbt.tag.INamedTag
import jp.katana.server.io.CompressException
import jp.katana.server.io.DecompressException
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.DeflaterOutputStream
import java.util.zip.InflaterOutputStream

@Suppress("UNCHECKED_CAST")
class NBTIO {
    companion object {
        fun write(tag: CompoundTag, endian: Endian = Endian.Little, isNetwork: Boolean = false): ByteArray {
            val stream = NBTStream(endian, isNetwork)
            try {
                tag.write(stream)
                return stream.getBuffer()
            } catch (e: IOException) {
                throw NBTIOException(e.toString())
            } catch (e: Exception) {
                throw NBTFormatException(e.toString())
            } finally {
                stream.close()
            }
        }

        fun read(buffer: ByteArray, endian: Endian = Endian.Little, isNetwork: Boolean = false): CompoundTag {
            val stream = NBTStream(endian, isNetwork)
            try {
                stream.setBuffer(buffer)

                val tag = CompoundTag("")
                tag.read(stream)

                return tag
            } catch (e: IOException) {
                throw NBTIOException(e.toString())
            } catch (e: Exception) {
                throw NBTFormatException(e.toString())
            } finally {
                stream.close()
            }
        }

        fun writeTag(tag: INamedTag, endian: Endian = Endian.Little, isNetwork: Boolean = false): ByteArray {
            val stream = NBTStream(endian, isNetwork)
            try {
                stream.writeByte(tag.type)
                stream.writeString(tag.name)
                tag.write(stream)

                return stream.getBuffer()
            } catch (e: IOException) {
                throw NBTIOException(e.toString())
            } catch (e: Exception) {
                throw NBTFormatException(e.toString())
            } finally {
                stream.close()
            }
        }

        fun readTag(buffer: ByteArray, endian: Endian = Endian.Little, isNetwork: Boolean = false): INamedTag {
            val stream = NBTStream(endian, isNetwork)
            try {
                stream.setBuffer(buffer)

                val type = stream.readByte()
                val tag = INamedTag.getTag(type, stream.readString())
                tag.read(stream)

                return tag
            } catch (e: IOException) {
                throw NBTIOException(e.toString())
            } catch (e: Exception) {
                throw NBTFormatException(e.toString())
            } finally {
                stream.close()
            }
        }

        fun readTagRemaining(
            buffer: ByteArray,
            endian: Endian = Endian.Little,
            isNetwork: Boolean = false
        ): Pair<INamedTag, ByteArray> {
            val stream = NBTStream(endian, isNetwork)
            try {
                stream.setBuffer(buffer)

                val type = stream.readByte()
                val tag = INamedTag.getTag(type, stream.readString())
                tag.read(stream)

                return tag to stream.readRemaining()
            } catch (e: IOException) {
                throw NBTIOException(e.toString())
            } catch (e: Exception) {
                throw NBTFormatException(e.toString())
            } finally {
                stream.close()
            }
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
            try {
                compresser.write(buffer)
            } catch (e: Exception) {
                throw CompressException(e.toString())
            } finally {
                compresser.close()
            }

            val buf = output.toByteArray()
            output.close()
            return buf
        }

        fun readZlibTag(buffer: ByteArray, endian: Endian = Endian.Little, isNetwork: Boolean = false): INamedTag {
            val payload = ByteArrayOutputStream()
            val decompresser = InflaterOutputStream(payload)
            try {
                decompresser.write(buffer)
            } catch (e: Exception) {
                throw DecompressException(e.toString())
            } finally {
                decompresser.close()
            }

            val buf = payload.toByteArray()
            payload.close()
            return readTag(buf, endian, isNetwork)
        }
    }
}