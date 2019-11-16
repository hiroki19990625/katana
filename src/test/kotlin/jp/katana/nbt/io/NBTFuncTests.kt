package jp.katana.nbt.io

import jp.katana.nbt.tag.*
import org.junit.jupiter.api.Test

class NBTFuncTests {
    @Test
    fun useTests() {
        val tag = nCompound("") {
            tByte("test", 5)
            tCompound("com2") {
                tList("list", INamedTag.LIST) {
                    tList("list2", INamedTag.BYTE) {
                        tByte("a", 5)
                    }
                }
                tCompound("com3") {

                }
            }
        }

        println(tag)
    }
}