package jp.katana.core.resourcepack

import jp.katana.core.data.IResourcePack
import java.io.File

interface IResourcePackManager {
    val packDirectory: File

    fun loadPack(pack: File)
    fun unloadPack(pack: File)

    fun getResourcePacks(): List<IResourcePack>
}