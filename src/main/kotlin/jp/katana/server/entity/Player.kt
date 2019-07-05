package jp.katana.server.entity

import jp.katana.core.entity.IPlayer
import java.net.InetSocketAddress

class Player(override val address: InetSocketAddress) : IPlayer {
}