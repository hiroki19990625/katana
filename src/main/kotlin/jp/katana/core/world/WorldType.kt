package jp.katana.core.world

enum class WorldType {
    Default,
    Flat,
    Empty,
    Customize
}

fun String.toWorldType(): WorldType {
    return when (this.toLowerCase()) {
        "default" -> WorldType.Default
        "flat" -> WorldType.Flat
        "custom" -> WorldType.Customize
        "empty" -> WorldType.Empty
        else -> WorldType.valueOf(this)
    }
}