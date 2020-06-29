package jp.katana.server.actor.data

import jp.katana.core.actor.data.IActorData
import jp.katana.utils.BinaryStream

class ShortActorData(override var value: Short = 0) : IActorData<Short> {
    override val type: Int = IActorData.SHORT

    override fun read(stream: BinaryStream) {
        value = stream.readShortLE()
    }

    override fun write(stream: BinaryStream) {
        stream.writeShortLE(value)
    }
}