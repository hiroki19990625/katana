package jp.katana.nbt.tag

import jp.katana.nbt.io.NBTStream

class EndTag : INamedTag {
    override var name: String = ""
    override val type: Byte = INamedTag.END

    override fun write(stream: NBTStream) {

    }

    override fun read(stream: NBTStream) {

    }

    override fun toString(): String {
        val builder = StringBuilder()
        print(builder)
        return builder.toString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent(indent)
        builder.append("${this.javaClass.simpleName} : $name\n")
    }
}