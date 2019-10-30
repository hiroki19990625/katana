package jp.katana.core.world

enum class WorldType {
    Default,
    Flat,
    Empty,
    Customize
}

fun String.toWorldType(): WorldType {
    return WorldType.valueOf(this)
}