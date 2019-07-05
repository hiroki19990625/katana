package jp.katana.core.network

import jp.katana.core.entity.IPlayer
import java.net.InetSocketAddress

interface IPlayerManager {
    fun addPlayer(address: InetSocketAddress, player: IPlayer): Boolean

    fun removePlayer(address: InetSocketAddress): Boolean

    fun getPlayer(address: InetSocketAddress): IPlayer?

    fun getPlayers(): List<IPlayer>
}