package jp.katana.server

/**
 * サーバーの高度な設定ファイル
 * @property serverTickRate サーバーの更新レート
 * @property logLevel サーバーのログレベル
 */
class KatanaConfig {
    var serverTickRate: Byte = 20

    var logLevel: String = "INFO"
}