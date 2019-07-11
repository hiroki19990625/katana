package jp.katana.core.factory

/**
 * 拡張可能なデータファクトリの管理を実装します。
 */
interface IFactoryManager {
    /**
     * ファクトリを登録します。
     * @param handler 登録するデータファクトリ
     */
    fun <K, V> register(handler: IFactory<K, V>)

    /**
     * ファクトリを取得します。
     * @param clazz 取得するデータファクトリの型データ
     * @return 取得したデータファクトリ
     */
    fun <F : IFactory<*, *>> get(clazz: Class<F>): F?
}