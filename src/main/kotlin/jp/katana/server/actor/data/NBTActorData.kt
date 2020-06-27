package jp.katana.server.actor.data

import jp.katana.core.actor.data.IActorData
import jp.katana.nbt.Endian
import jp.katana.nbt.io.NBTIO
import jp.katana.nbt.tag.CompoundTag
import jp.katana.utils.BinaryStream

class NBTActorData(override var value: CompoundTag = CompoundTag("")) : IActorData<CompoundTag> {
    override val type: Int = IActorData.NBT

    override fun read(stream: BinaryStream) {
        val result = NBTIO.readTagRemaining(stream.readRemaining(), Endian.Little, true)
        value = result.first as CompoundTag
        stream.setBuffer(result.second)
    }

    override fun write(stream: BinaryStream) {
        stream.write(NBTIO.write(value, Endian.Little, true))
    }
}