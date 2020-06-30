package jp.katana.debug

interface IPrintable {
    fun print(builder: StringBuilder, indent: Int)

    fun IPrintable.toPrintString(): String {
        val builder = StringBuilder()
        print(builder, 0)
        return builder.toString()
    }
}