package jp.katana.core.debug

interface IPrintable {
    fun print(builder: StringBuilder, indent: Int)

    fun StringBuilder.appendIndent(indent: Int) {
        for (i in 0..indent)
            this.append('\t')
    }
}