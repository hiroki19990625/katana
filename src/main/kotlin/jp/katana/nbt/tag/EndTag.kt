package jp.katana.nbt.tag

import jp.katana.nbt.io.NBTStream

class EndTag : INamedTag {
    override var name: String = ""
    override val type: Byte = INamedTag.END

    override fun write(stream: NBTStream) {

    }

    override fun read(stream: NBTStream) {

    }
}