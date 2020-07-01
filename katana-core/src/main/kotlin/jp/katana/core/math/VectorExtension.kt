package jp.katana.core.math

import jp.katana.core.world.BlockPosition
import jp.katana.core.world.IWorld
import jp.katana.core.world.Position
import jp.katana.math.Vector2
import jp.katana.math.Vector3

fun Vector3.toPosition(world: IWorld): Position {
    return Position(world, Vector3(x, y, z))
}

fun Vector3.toBlockPosition(world: IWorld): BlockPosition {
    return BlockPosition(world, Vector3(x, y, z).toVector3Int())
}

fun Vector3.toPositionXY(world: IWorld): Position {
    return Position(world, Vector3(x, y, 0.0))
}

fun Vector3.toPositionXZ(world: IWorld): Position {
    return Position(world, Vector3(x, 0.0, y))
}

fun Vector2.toBlockPositionXY(world: IWorld): BlockPosition {
    return BlockPosition(world, Vector3(x, y, 0.0).toVector3Int())
}

fun Vector2.toBlockPositionXZ(world: IWorld): BlockPosition {
    return BlockPosition(world, Vector3(x, 0.0, y).toVector3Int())
}