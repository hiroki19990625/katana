package jp.katana.core

interface IClientProperties {
    var serverIp: String
    var serverPort: Int
    var userName: String

    var tickRate: Byte
}