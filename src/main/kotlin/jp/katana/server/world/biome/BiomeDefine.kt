package jp.katana.server.world.biome

import jp.katana.core.world.biome.IBiomeDefine

class BiomeDefine(
    override val name: String,
    override val blueSpores: Float,
    override val whiteAsh: Float,
    override val ash: Float,
    override val temperature: Float,
    override val redSpores: Float,
    override val downfall: Float
) : IBiomeDefine