package jp.katana.core.world

enum class WorldType {
    Default,
    Flat,
    Empty,
    Customize,
    Old
}

fun String.toWorldType(): WorldType {
    return when (this.toLowerCase()) {
        "default" -> WorldType.Default
        "flat" -> WorldType.Flat
        "custom" -> WorldType.Customize
        "empty" -> WorldType.Empty
        "old" -> WorldType.Old
        else -> WorldType.valueOf(this)
    }
}

fun String.toWorldTypeId(): Int {
    return when (this.toLowerCase()) {
        "default" -> 1
        "flat" -> 0
        "custom" -> 1
        "empty" -> 0
        "old" -> 2
        else -> 0
    }
}

fun Int.toWorldType(): WorldType {
    return when (this) {
        1 -> WorldType.Default
        0 -> WorldType.Flat
        2 -> WorldType.Old
        else -> WorldType.Default
    }
}

fun WorldType.toStringValue(): String {
    return when (this) {
        WorldType.Default -> "default"
        WorldType.Flat -> "flat"
        WorldType.Customize -> "custom"
        WorldType.Empty -> "empty"
        WorldType.Old -> "old"
    }
}

fun WorldType.toIntValue(): Int {
    return when (this) {
        WorldType.Default -> 1
        WorldType.Flat -> 0
        WorldType.Customize -> 1
        WorldType.Empty -> 0
        WorldType.Old -> 2
    }
}