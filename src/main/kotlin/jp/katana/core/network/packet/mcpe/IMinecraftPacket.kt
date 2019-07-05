package jp.katana.core.network.packet.mcpe

interface IMinecraftPacket {
    val packetId: Int
    val channel: Int

    fun decode()
    fun encode()
}