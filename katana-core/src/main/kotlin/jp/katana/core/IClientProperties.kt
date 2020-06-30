package jp.katana.core

interface IClientProperties {
    var serverIp: String
    var serverPort: Int
    var userName: String

    var logLevel: String

    var tickRate: Byte
}