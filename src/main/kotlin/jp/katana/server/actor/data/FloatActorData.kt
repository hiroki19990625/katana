package jp.katana.server.actor.data

import jp.katana.core.actor.data.IActorData
import jp.katana.utils.BinaryStream

class FloatActorData(override var value: Float = 0f) : IActorData<Float> {
    override val type: Int = IActorData.FLOAT

    override fun read(stream: BinaryStream) {
        value = stream.readFloatLE()
    }

    override fun write(stream: BinaryStream) {
        stream.writeFloatLE(value)
    }
}