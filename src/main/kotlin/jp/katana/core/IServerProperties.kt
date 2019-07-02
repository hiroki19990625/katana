package jp.katana.core

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
    var viewDistance: Short
    var whiteList: Boolean
}