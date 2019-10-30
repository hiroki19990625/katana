package jp.katana.core.world.chunk

interface IChunkLoader {
    fun getLoaderId(): Long
    fun getRadius(): Int
}