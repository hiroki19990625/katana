package jp.katana.core.entity

interface IEntityDefine {
    val hasSpawnEgg: Boolean
    val experimental: Boolean
    val summonable: Boolean
    val id: String
    val bid: String
    val rid: Int
}