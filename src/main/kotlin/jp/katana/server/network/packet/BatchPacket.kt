package jp.katana.server.network.packet

import jp.katana.server.network.packet.mcpe.MinecraftProtocols
import jp.katana.server.utils.BinaryStream
import org.apache.logging.log4j.LogManager
import java.util.zip.Deflater
import java.util.zip.Inflater


class BatchPacket : BinaryStream() {
    var isEncrypt = false
    var payload: ByteArray = ByteArray(0)
    val logger = LogManager.getLogger()

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
        }
    }

    fun encode() {
        writeByte(MinecraftProtocols.BATCH_PACKET)

        if (!isEncrypt) {
            val output = ByteArray(1024 * 1024 * 64)
            val compresser = Deflater()
            compresser.setInput(payload)
            compresser.finish()
            val length = compresser.deflate(output)
            compresser.end()

            write(output.copyOf(length))
        }
    }
}