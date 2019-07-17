package jp.katana.server.factory

import jp.katana.server.network.packet.mcpe.*

class PacketFactory : SimpleFactory<Int, MinecraftPacket>() {
    init {
        this += LoginPacket()
        this += PlayStatusPacket()
        this += ServerToClientHandshakePacket()
        this += ClientToServerHandshakePacket()
        this += DisconnectPacket()
        this += ResourcePacksInfoPacket()
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