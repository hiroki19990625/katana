package jp.katana.core.data

import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.FileInputStream


interface IResourcePackInfo {
    val file: File?

    val packId: String
    val packVersion: String
    val packSize: Long

    val encryptionKey: String
    val subPackName: String
    val contentIdentity: String

    val unknownBool: Boolean

    val hash: ByteArray

    fun getDataChunk(offset: Int, length: Int): ByteArray {
        val chunk: ByteArray = if (this.packSize - offset > length) {
            ByteArray(length)
        } else {
            ByteArray((this.packSize - offset).toInt())
        }

        try {
            FileInputStream(file!!).use { fis ->
                fis.skip(offset.toLong())
                fis.read(chunk)
            }
        } catch (e: Exception) {
            LogManager.getLogger().error(e)
        }

        return chunk
    }
}