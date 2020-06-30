package jp.katana.core.network

import jp.katana.core.actor.IActorPlayer
import java.net.InetSocketAddress

interface IPlayerManager {
    /**
     * プレイヤーの追加を行います。
     * @param address プレイヤーのIPアドレスとポート
     * @param player プレイヤーの実装
     * @return 実行の結果
     */
    fun addPlayer(address: InetSocketAddress, player: IActorPlayer): Boolean

    /**
     * プレイヤーの削除を行います。
     * @param address プレイヤーのIPアドレスとポート
     * @return 実行の結果
     */
    fun removePlayer(address: InetSocketAddress): Boolean

    /**
     * プレイヤーを取得します。
     * @param address プレイヤーのIPアドレスとポート
     * @return 取得したプレイヤー
     */
    fun getPlayer(address: InetSocketAddress): IActorPlayer?

    /**
     * 追加されたプレイヤーを全て取得します。
     * @return 追加された全てのプレイヤー
     */
    fun getPlayers(): List<IActorPlayer>
}