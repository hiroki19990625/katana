package jp.katana.core.network.packet.mcpe

/**
 * Minecraftで使用データパケットのベースを提供します。
 * @property packetId パケットのID
 * @property channel パケットの送信チャンネル
 */
interface IMinecraftPacket {
    val packetId: Int
    val channel: Int

    /**
     * パケットをデコードします。
     */
    fun decode()

    /**
     * パケットをエンコードします。
     */
    fun encode()
}