package jp.katana.core.actor

import jp.katana.core.actor.attribute.IActorAttributes
import jp.katana.core.actor.data.IActorDataManager
import jp.katana.core.world.IWorld
import jp.katana.math.Vector3
import java.util.*

interface IActor {
    val uuid: UUID

    val world: IWorld?

    val position: Vector3
    val yaw: Double
    val pitch: Double

    val attributes: IActorAttributes
    val data: IActorDataManager
}