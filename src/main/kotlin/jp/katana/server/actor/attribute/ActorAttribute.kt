package jp.katana.server.actor.attribute

import jp.katana.core.actor.attribute.IActorAttribute

class ActorAttribute(
    override var name: String,
    override var maxValue: Float,
    override var minValue: Float,
    override var value: Float,
    override var defaultValue: Float
) : IActorAttribute