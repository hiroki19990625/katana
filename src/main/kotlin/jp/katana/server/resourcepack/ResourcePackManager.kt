package jp.katana.server.resourcepack

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jp.katana.core.data.IResourcePack
import jp.katana.core.resourcepack.IResourcePackManager
import jp.katana.i18n.I18n
import jp.katana.server.Server
import jp.katana.server.data.ResourcePack
import java.io.File
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.zip.ZipFile

class ResourcePackManager(private val server: Server) : IResourcePackManager {
    override val packDirectory: File = File("resource_packs")

    private val resourcePacks: MutableList<IResourcePack> = mutableListOf()

    init {
        if (packDirectory.isDirectory && packDirectory.exists()) {
            for (packFile in packDirectory.listFiles()!!) {
                if (packFile.isFile && packFile.extension == "zip") {
                    loadPack(packFile)
                }
            }
        } else {
            packDirectory.mkdir()
        }
    }

    override fun loadPack(pack: File) {
        val zipFile = ZipFile(pack, Charset.forName("Shift-JIS"))
        val entry = zipFile.getEntry("manifest.json")
        if (entry != null && !entry.isDirectory) {
            val stream = zipFile.getInputStream(entry)
            val packInfo = readJson(stream.readBytes(), pack)
            if (packInfo != null)
                resourcePacks.add(packInfo)

            stream.close()
        }
        zipFile.close()
    }

    override fun unloadPack(pack: File) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getResourcePacks(): List<IResourcePack> {
        return resourcePacks.toList()
    }

    private fun readJson(bytes: ByteArray, pack: File): IResourcePack? {
        val jsonParser = JsonParser()
        val data = jsonParser.parse(String(bytes, Charset.forName("utf8")))
        val packLength = pack.length()
        val stream = pack.inputStream()
        val hash = MessageDigest.getInstance("SHA-256").digest(stream.readBytes())
        if (data is JsonObject && validate(data)) {
            val formatVersion = data["format_version"].asInt
            val header = data["header"].asJsonObject
            val name = header["name"].asString
            val uuid = header["uuid"].asString
            val version = header["version"].asJsonArray
            val versionStr = String.format("%s.%s.%s", version[0].asInt, version[1].asInt, version[2].asInt)

            server.logger.info(I18n["katana.server.resourcePack.load", name, versionStr])
            server.logger.info(packLength)
            return ResourcePack(uuid, versionStr, packLength, "", "", "", false, hash)
        }
        stream.close()

        return null
    }

    private fun validate(jsonObject: JsonObject): Boolean {
        return jsonObject.has("format_version") &&
                jsonObject.has("header") &&
                jsonObject.has("modules")
    }
}