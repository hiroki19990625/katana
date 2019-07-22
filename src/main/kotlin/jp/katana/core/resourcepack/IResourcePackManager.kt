package jp.katana.core.resourcepack

import java.io.File

interface IResourcePackManager {
    val packDirectory: File

    fun loadPack(pack: File)
    fun unloadPack(pack: File)
}