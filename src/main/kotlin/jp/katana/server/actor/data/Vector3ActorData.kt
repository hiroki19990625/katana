package jp.katana.server.actor.data

import jp.katana.core.actor.data.IActorData
import jp.katana.math.Vector3
import jp.katana.utils.BinaryStream

class Vector3ActorData(override var value: Vector3 = Vector3(0.0, 0.0, 0.0)) : IActorData<Vector3> {
    override val type: Int = IActorData.VECTOR3F

    override fun read(stream: BinaryStream) {
        value = stream.readVector3()
    }

    override fun write(stream: BinaryStream) {
        stream.writeVector3(value)
    }
}