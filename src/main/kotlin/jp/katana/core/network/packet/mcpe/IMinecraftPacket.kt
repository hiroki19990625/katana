package jp.katana.core.network.packet.mcpe

interface IMinecraftPacket {
    val channel: Int

    fun decode()
    fun encode()
}