package jp.katana.server.network.packet

import jp.katana.i18n.I18n
import jp.katana.server.io.CompressException
import jp.katana.server.io.DecompressException
import jp.katana.server.network.DecryptException
import jp.katana.server.network.EncryptException
import jp.katana.server.network.packet.mcpe.MinecraftProtocols
import jp.katana.utils.BinaryStream
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.util.*
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream
import java.util.zip.Inflater
import java.util.zip.InflaterOutputStream
import javax.crypto.Cipher


class BatchPacket(val inflater: Inflater?, val deflater: Deflater?) : BinaryStream() {
    companion object {
        private val logger: Logger = LogManager.getLogger()
    }

    var isEncrypt = false
    var encryptCounter: Long = 0
    var decryptCounter: Long = 0

    var decrypt: Cipher? = null
    var encrypt: Cipher? = null
    var sharedKey: ByteArray? = null

    var payload: ByteArray = ByteArray(0)

    fun decode() {
        readByte()

        if (!isEncrypt) {
            try {
                val outPayload = ByteArrayOutputStream()
                val decompresser = InflaterOutputStream(outPayload, inflater)
                decompresser.write(readRemaining())
                decompresser.close()

                this.payload = outPayload.toByteArray()
                outPayload.close()
            } catch (e: Exception) {
                throw DecompressException(e.toString())
            }
        } else {
            val buffer = decrypt!!.update(readRemaining())
            val payload = ByteArray(buffer.size - 8)
            val outPayload = ByteArrayOutputStream()
            val calculateCheckSum = ByteArray(8)
            System.arraycopy(buffer, 0, payload, 0, buffer.size - 8)
            System.arraycopy(buffer, buffer.size - 8, calculateCheckSum, 0, 8)

            val binaryStream = BinaryStream()
            binaryStream.writeLongLE(decryptCounter)
            binaryStream.write(payload)
            binaryStream.write(sharedKey!!)

            try {
                val messageDigest = MessageDigest.getInstance("SHA-256")
                messageDigest.update(binaryStream.array())
                val result = messageDigest.digest()
                val checkSum = ByteArray(8)
                System.arraycopy(result, 0, checkSum, 0, 8)

                if (!Arrays.equals(calculateCheckSum, checkSum)) {
                    throw IOException(I18n["katana.server.network.decryptFailed"])
                }
            } catch (e: Exception) {
                throw DecryptException(e)
            }

            try {
                val decompresser = InflaterOutputStream(outPayload, inflater)
                decompresser.write(payload)
                decompresser.close()

                this.payload = outPayload.toByteArray()
                outPayload.close()
            } catch (e: Exception) {
                throw DecompressException(e.toString())
            }

            binaryStream.close()
        }
    }

    fun encode() {
        writeByte(MinecraftProtocols.BATCH_PACKET.toByte())

        val output = ByteArrayOutputStream()
        try {
            val compresser = DeflaterOutputStream(output, deflater)
            compresser.write(payload)
            compresser.close()
        } catch (e: Exception) {
            throw CompressException(e.toString())
        }

        var buffer = output.toByteArray()
        output.close()
        if (isEncrypt) {
            val binaryStream = BinaryStream()
            binaryStream.writeLongLE(encryptCounter)
            binaryStream.write(buffer)
            binaryStream.write(sharedKey!!)

            try {
                val messageDigest = MessageDigest.getInstance("SHA-256")
                messageDigest.update(binaryStream.array())
                val result = messageDigest.digest()
                val checkSum = ByteArray(8)
                System.arraycopy(result, 0, checkSum, 0, 8)

                val digst = BinaryStream()
                digst.write(buffer)
                digst.write(checkSum)

                buffer = encrypt!!.update(digst.array())

                digst.close()
            } catch (e: Exception) {
                throw EncryptException(e)
            }

            binaryStream.close()
        }

        write(buffer)
    }
}