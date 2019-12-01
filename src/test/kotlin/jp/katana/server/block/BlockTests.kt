package jp.katana.server.block

import org.junit.jupiter.api.Test

class BlockTests {
    @Test
    fun createBlockTest() {
        val blockDef = BlockDefinitions()
        val block = BlockStone()
        println(block.runtimeId)
        val block2 = BlockStone(BlockStone.StoneType.Andesite)
        println(block2.runtimeId)
        block.stoneType = BlockStone.StoneType.GraniteSmooth
        println(block.runtimeId)
    }
}