package jp.katana.server.utils

/**
 * クラスの生成を提供します。
 */
class ClassGenerator {
    companion object {
        /**
         * ジェネリクスクラスを生成します。
         * @return 生成されたジェネリクスクラス
         */
        inline fun <reified T> generateClass(): Class<T> {
            return T::class.java
        }
    }
}