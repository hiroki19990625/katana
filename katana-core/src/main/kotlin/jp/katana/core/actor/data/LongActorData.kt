package jp.katana.core.actor.data

import jp.katana.core.actor.data.IActorData
import jp.katana.utils.BinaryStream

class LongActorData(override var value: Long = 0) : IActorData<Long> {
    override val type: Int = IActorData.LONG

    override fun read(stream: BinaryStream) {
        value = stream.readVarLong()
    }

    override fun write(stream: BinaryStream) {
        stream.writeVarLong(value)
    }
}