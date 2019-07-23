package jp.katana.core.resourcepack

import jp.katana.core.data.IResourcePackInfo
import java.io.File

interface IResourcePackManager {
    val packDirectory: File

    fun loadPack(pack: File)
    fun unloadPack(uuid: String)

    fun getResourcePack(uuid: String): IResourcePackInfo?
    fun getResourcePacks(): List<IResourcePackInfo>
}