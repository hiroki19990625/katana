package jp.katana.server.network.packet

import jp.katana.server.network.packet.mcpe.MinecraftProtocols
import jp.katana.server.utils.BinaryStream
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import sun.audio.AudioPlayer.player
import java.io.IOException
import java.security.MessageDigest
import java.util.*
import java.util.zip.Deflater
import java.util.zip.Inflater
import javax.crypto.Cipher


class BatchPacket : BinaryStream() {
    companion object {
        private val logger: Logger = LogManager.getLogger()
    }

    var isEncrypt = false
    var encryptCounter: Long = 0
    var decryptCounter: Long = 0

    val decrypt: Cipher? = null
    val encrypt: Cipher? = null
    val sharedKey: ByteArray = ByteArray(0)

    var payload: ByteArray = ByteArray(0)

    fun decode() {
        readByte()

        if (!isEncrypt) {
            val payload = ByteArray(1024 * 1024 * 64)
            val decompresser = Inflater()
            decompresser.setInput(read(remaining()))

            try {
                val length = decompresser.inflate(payload)
                decompresser.end()

                this.payload = payload.copyOf(length)
            } catch (e: Exception) {
                logger.error("", e)
            }
        } else {
            val buffer = decrypt!!.update(payload)

            val payload = ByteArray(buffer.size - 8)
            val calculateCheckSum = ByteArray(8)
            System.arraycopy(buffer, 0, payload, 0, buffer.size - 8)
            System.arraycopy(buffer, buffer.size - 8, calculateCheckSum, 0, 8)

            val binaryStream = BinaryStream()
            binaryStream.writeLongLE(decryptCounter)
            binaryStream.write(payload)
            binaryStream.write(sharedKey)

            val messageDigest = MessageDigest.getInstance("SHA-256")
            messageDigest.update(binaryStream.array())
            val result = messageDigest.digest()
            val checkSum = ByteArray(8)
            System.arraycopy(result, 0, checkSum, 0, 8)

            if (!Arrays.equals(calculateCheckSum, checkSum)) {
                throw IOException("Not Decrypt")
            }

            val decompresser = Inflater()
            decompresser.setInput(read(remaining()))

            try {
                val length = decompresser.inflate(payload)
                decompresser.end()

                this.payload = payload.copyOf(length)
            } catch (e: Exception) {
                logger.error("", e)
            }
        }
    }

    fun encode() {
        writeByte(MinecraftProtocols.BATCH_PACKET)

        val output = ByteArray(1024 * 1024 * 64)
        val compresser = Deflater()
        compresser.setInput(payload)
        compresser.finish()
        val length = compresser.deflate(output)
        compresser.end()

        var buffer = output.copyOf(length)
        if (!isEncrypt) {
            val binaryStream = BinaryStream()
            binaryStream.writeLongLE(encryptCounter)
            binaryStream.write(payload)
            binaryStream.write(sharedKey)

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
            } catch (e: Exception) {
                logger.warn("Encrypt Error!")
            }
        }

        write(buffer)
    }
}