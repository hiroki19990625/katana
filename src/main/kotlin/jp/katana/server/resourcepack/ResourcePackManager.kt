package jp.katana.server.resourcepack

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jp.katana.core.data.IResourcePackInfo
import jp.katana.core.resourcepack.IResourcePackManager
import jp.katana.i18n.I18n
import jp.katana.server.Server
import jp.katana.server.data.ResourcePackInfo
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.security.MessageDigest
import java.util.zip.ZipFile

class ResourcePackManager(private val server: Server) : IResourcePackManager {
    override val packDirectory: File = File("resource_packs")

    private val resourcePacks: MutableMap<String, IResourcePackInfo> = mutableMapOf()

    init {
        if (packDirectory.isDirectory && packDirectory.exists()) {
            for (packFile in packDirectory.listFiles()!!) {
                try {
                    if (packFile.isFile && (packFile.extension == "zip" || packFile.extension == "mcpack")) {
                        loadPack(packFile)
                    }
                } catch (e: InvalidResourcePackException) {
                    server.logger.warn("", e)
                } catch (e: ResourcePackFormatException) {
                    server.logger.error("", e)
                }
            }
        } else {
            packDirectory.mkdir()
        }
    }

    override fun loadPack(pack: File) {
        val zipFile = ZipFile(pack, Charset.forName("utf8"))
        val entry = zipFile.getEntry("manifest.json")
        if (entry != null && !entry.isDirectory) {
            val stream = zipFile.getInputStream(entry)
            val packInfo = readJson(stream.readBytes(), pack)
            if (packInfo != null)
                resourcePacks.put(packInfo.packId, packInfo)

            stream.close()
        } else {
            throw InvalidResourcePackException(pack.name)
        }
        zipFile.close()
    }

    override fun unloadPack(uuid: String) {
        if (resourcePacks.containsKey(uuid)) {
            resourcePacks.remove(uuid)
        }
    }

    override fun getResourcePack(uuid: String): IResourcePackInfo? {
        return resourcePacks[uuid]
    }

    override fun getResourcePacks(): List<IResourcePackInfo> {
        return resourcePacks.values.toList()
    }

    private fun readJson(bytes: ByteArray, pack: File): IResourcePackInfo? {
        val jsonParser = JsonParser()
        val data = jsonParser.parse(String(bytes, Charset.forName("utf8")))
        val bytes = Files.readAllBytes(pack.toPath())
        val hash = MessageDigest.getInstance("SHA-256").digest(bytes)
        if (data is JsonObject && validate(data)) {
            val formatVersion = data["format_version"].asInt
            val header = data["header"].asJsonObject
            val name = header["name"].asString
            val uuid = header["uuid"].asString
            val version = header["version"].asJsonArray
            val versionStr = String.format("%s.%s.%s", version[0].asInt, version[1].asInt, version[2].asInt)

            server.logger.info(I18n["katana.server.resourcePack.load", name, versionStr])
            return ResourcePackInfo(pack, uuid, versionStr, bytes.size.toLong(), "", "", "", false, hash)
        } else {
            throw ResourcePackFormatException(pack.name)
        }
    }

    private fun validate(jsonObject: JsonObject): Boolean {
        return jsonObject.has("format_version") &&
                jsonObject.has("header") &&
                jsonObject.has("modules")
    }
}