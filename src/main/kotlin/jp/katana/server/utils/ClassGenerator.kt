package jp.katana.server.utils

class ClassGenerator {
    companion object {
        inline fun <reified T> generateClass(): Class<T> {
            return T::class.java
        }
    }
}