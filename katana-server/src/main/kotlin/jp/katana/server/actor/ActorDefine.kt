package jp.katana.server.actor

import jp.katana.core.actor.IActorDefine

class ActorDefine(
    override val hasSpawnEgg: Boolean,
    override val experimental: Boolean,
    override val summonable: Boolean,
    override val id: String,
    override val bid: String,
    override val rid: Int
) : IActorDefine