package jp.katana.server.block

import jp.katana.nbt.tag.StringTag

class BlockStone : Block {
    constructor() : this(StoneType.Stone)
    constructor(stoneType: StoneType) : super(BlockDefinitions.fromIdAllStates(STONE)) {
        this.stoneType = stoneType
    }

    var stoneType: StoneType = StoneType.Stone
        set(value) {
            nowState = blockStates.first { e -> (e["stone_type"] as StringTag).value == value.state }
            field = value
        }

    enum class StoneType private constructor(public val state: String) {
        Stone("stone"),
        Andesite("andesite"),
        Diorite("diorite"),
        Granite("granite"),
        AndesiteSmooth("andesite_smooth"),
        DioriteSmooth("diorite_smooth"),
        GraniteSmooth("granite_smooth");
    }
}