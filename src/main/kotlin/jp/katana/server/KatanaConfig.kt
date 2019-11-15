package jp.katana.server

/**
 * サーバーの高度な設定ファイル
 * @property serverTickRate サーバーの更新レート
 * @property logLevel サーバーのログレベル
 */
class KatanaConfig {
    var serverTickRate: Byte = 20

    var showSendPacketId: Boolean = false
    var showHandlePacketId: Boolean = false
    var sendPacketDump: Boolean = false
    var handlePacketDump: Boolean = false
    var printSendPacket: Boolean = false
    var printHandlePacket: Boolean = false

    var logLevel: String = "INFO"
}