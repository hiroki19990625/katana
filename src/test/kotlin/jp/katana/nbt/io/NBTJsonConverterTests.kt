package jp.katana.nbt.io

import org.junit.jupiter.api.Test

class NBTJsonConverterTests {
    @Test
    fun convertTests() {
        println(NBTJsonConverter.jsonToNBT("{'aaa': 100, 'bbb': 100b, 'ccc': 1000s, 'ddd': 10000L, 'eee': 1.0, 'fff': 54.0f, 'ggg': 'test', 'hhh': {'sss': 5}, 'iii': [5, 4, 3], 'jjj': [{'a': 1}, {'b': 2}]}").toString())
    }
}