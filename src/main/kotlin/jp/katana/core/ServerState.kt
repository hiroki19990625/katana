package jp.katana.core

/**
 * サーバーのステータスを実装します。
 */
enum class ServerState {
    Starting,
    Running,
    Stopping,
    Stopped
}