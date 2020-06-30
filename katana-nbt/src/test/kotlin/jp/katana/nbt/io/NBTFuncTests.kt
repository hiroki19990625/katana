package jp.katana.nbt.io

import jp.katana.nbt.tag.*
import org.junit.jupiter.api.Test

class NBTFuncTests {
    @Test
    fun useTests() {
        val tag = nCompound("") {
            comByte("test", 5)
            comCompound("com2") {
                comList("list", INamedTag.LIST) {
                    listList(INamedTag.BYTE) {
                        listByte(5)
                    }
                }
                comCompound("com3") {
                    comList("listCom", INamedTag.COMPOUND) {
                        listCompound {
                            comInt("a", 0)
                            comString("name", "aaa")
                        }
                        listCompound {
                            comInt("id", 0)
                            comCompound("data") {
                                comInt("aaa", 1)
                            }
                        }
                    }
                }
            }
        }

        println(tag)
    }
}