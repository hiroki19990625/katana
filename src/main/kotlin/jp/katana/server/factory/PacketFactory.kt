package jp.katana.server.factory

import jp.katana.server.network.packet.mcpe.*

class PacketFactory : SimpleFactory<Int, MinecraftPacket>() {
    init {
        this += LoginPacket() // 0x01
        this += PlayStatusPacket() // 0x02
        this += ServerToClientHandshakePacket() // 0x03
        this += ClientToServerHandshakePacket() // 0x04
        this += DisconnectPacket() // 0x05
        this += ResourcePacksInfoPacket() // 0x06
        this += ResourcePackStackPacket() // 0x07
        this += ResourcePackClientResponsePacket() // 0x08

        this += ResourcePackDataInfoPacket() // 0x52
    }

    override fun plusAssign(value: MinecraftPacket) {
        if (!map.containsKey(value.packetId))
            map[value.packetId] = value
    }

    override operator fun get(name: Int): MinecraftPacket? {
        if (map.containsKey(name))
            return map[name]?.javaClass?.newInstance()

        return null
    }
}