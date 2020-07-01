package jp.katana.core.actor.attribute

interface IActorAttribute {
    val name: String

    val maxValue: Float
    val minValue: Float
    val value: Float
    val defaultValue: Float
}