package jp.katana.core

/**
 * サーバーのプロパティファイルを実装します。
 * @property allowNethet Boolean
 * @property allowEnd Boolean
 * @property difficulty Byte
 * @property enableCommandBlock Boolean
 * @property levelName String
 * @property levelSeed String
 * @property levelType String
 * @property maxPlayer Int
 * @property motd String
 * @property networkCompressionThreshold Byte
 * @property secureMode Boolean
 * @property playerIdleTimeout Int
 * @property pvp Boolean
 * @property serverIp String
 * @property serverPort Int
 * @property subMotd String
 * @property viewDistance Short
 * @property whiteList Boolean
 */
interface IServerProperties {
    var allowNethet: Boolean
    var allowEnd: Boolean
    var difficulty: Byte
    var enableCommandBlock: Boolean
    var levelName: String
    var levelSeed: String
    var levelType: String
    var maxPlayer: Int
    var motd: String
    var networkCompressionThreshold: Byte
    var secureMode: Boolean
    var playerIdleTimeout: Int
    var pvp: Boolean
    var serverIp: String
    var serverPort: Int
    var subMotd: String
    var viewDistance: Short
    var whiteList: Boolean
}