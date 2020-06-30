package jp.katana.core.actor.data

import jp.katana.core.actor.data.IActorData
import jp.katana.utils.BinaryStream

class StringActorData(override var value: String = "") : IActorData<String> {
    override val type: Int = IActorData.STRING

    override fun read(stream: BinaryStream) {
        value = stream.readVarString()
    }

    override fun write(stream: BinaryStream) {
        stream.writeVarString(value)
    }
}