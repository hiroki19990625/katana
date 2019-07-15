package jp.katana.server.factory

import jp.katana.server.network.packet.mcpe.LoginPacket
import jp.katana.server.network.packet.mcpe.MinecraftPacket
import jp.katana.server.network.packet.mcpe.PlayStatusPacket

class PacketFactory : SimpleFactory<Int, MinecraftPacket>() {
    init {
        this += LoginPacket()
        this += PlayStatusPacket()
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