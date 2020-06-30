package jp.katana.client

import jp.katana.core.IClientProperties

class ClientProperties : IClientProperties {
    override var serverIp: String = "localhost"
    override var serverPort: Int = 19132
    override var userName: String = "Steve"

    override var logLevel: String = "INFO"

    override var tickRate: Byte = 20
}