package jp.katana.core.actor

interface IActorDefine {
    val hasSpawnEgg: Boolean
    val experimental: Boolean
    val summonable: Boolean
    val id: String
    val bid: String
    val rid: Int
}