package jp.katana.core.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.debug.IPrintable

/**
 * Minecraftで使用データパケットのベースを提供します。
 * @property packetId パケットのID
 * @property channel パケットの送信チャンネル
 */
interface IMinecraftPacket : IPrintable {
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

    /**
     * パケットのハンドル時に呼び出されます。
     * @param player IActorPlayer
     * @param server IServer
     */
    fun handleServer(player: IActorPlayer, server: IServer)

    // TODO: Handle Client
}