package jp.katana.server

import jp.katana.core.IServerProperties

/**
 * サーバーのプロパティファイルを提供します。
 **/
class ServerProperties : IServerProperties {
    override var allowNethet: Boolean = true
    override var allowEnd: Boolean = true
    override var difficulty: Byte = 2
    override var enableCommandBlock: Boolean = false
    override var forceResource: Boolean = false
    override var levelName: String = "world"
    override var levelSeed: String = "-1"
    override var levelType: String = "default"
    override var maxPlayer: Int = 20
    override var motd: String = "Katana Server"
    override var networkCompressionThreshold: Byte = 2
    override var secureMode: Boolean = true
    override var playerIdleTimeout: Int = 0
    override var pvp: Boolean = true
    override var serverIp: String = "0.0.0.0"
    override var serverPort: Int = 19132
    override var subMotd: String = ""
    override var viewDistance: Short = 8
    override var whiteList: Boolean = false
}
