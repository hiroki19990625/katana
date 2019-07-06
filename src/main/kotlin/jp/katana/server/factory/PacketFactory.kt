package jp.katana.server.factory

import jp.katana.server.network.packet.mcpe.MinecraftPacket

class PacketFactory : SimpleFactory<Int, MinecraftPacket>() {
    init {

    }

    override fun plusAssign(value: MinecraftPacket) {
        if (!map.containsKey(value.packetId))
            map[value.packetId] = value
    }
}