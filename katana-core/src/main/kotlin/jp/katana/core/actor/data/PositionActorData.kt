package jp.katana.core.actor.data

import jp.katana.core.utils.readBlockPosition
import jp.katana.core.utils.writeBlockPosition
import jp.katana.math.Vector3Int
import jp.katana.utils.BinaryStream

class PositionActorData(override var value: Vector3Int = Vector3Int(0, 0, 0)) : IActorData<Vector3Int> {
    override val type: Int = IActorData.POS

    override fun read(stream: BinaryStream) {
        value = stream.readBlockPosition()
    }

    override fun write(stream: BinaryStream) {
        stream.writeBlockPosition(value)
    }
}