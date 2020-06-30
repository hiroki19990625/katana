package jp.katana.core.actor.data

import jp.katana.core.actor.data.IActorData
import jp.katana.utils.BinaryStream

class IntActorData(override var value: Int = 0) : IActorData<Int> {
    override val type: Int = IActorData.INT

    override fun read(stream: BinaryStream) {
        value = stream.readVarInt()
    }

    override fun write(stream: BinaryStream) {
        stream.writeVarInt(value)
    }
}