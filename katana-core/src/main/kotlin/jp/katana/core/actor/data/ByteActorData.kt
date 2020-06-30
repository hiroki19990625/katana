package jp.katana.core.actor.data

import jp.katana.core.actor.data.IActorData
import jp.katana.utils.BinaryStream

class ByteActorData(override var value: Byte = 0) : IActorData<Byte> {
    override val type: Int = IActorData.BYTE

    override fun read(stream: BinaryStream) {
        value = stream.readByte()
    }

    override fun write(stream: BinaryStream) {
        stream.writeByte(value)
    }
}