package jp.katana.server.console

import jp.katana.core.ServerState
import jp.katana.core.console.IKatanaConsole
import jp.katana.server.Server
import net.minecrell.terminalconsole.SimpleTerminalConsole
import org.jline.reader.LineReader
import org.jline.reader.LineReaderBuilder

class KatanaConsole(private val server: Server) : SimpleTerminalConsole(), IKatanaConsole {
    override fun isRunning(): Boolean {
        return server.state == ServerState.Running
    }

    override fun runCommand(command: String?) {
        server.logger.info("test: " + command!!)
    }

    override fun buildReader(builder: LineReaderBuilder?): LineReader {
        builder?.appName("Katana")
        builder?.option(LineReader.Option.HISTORY_BEEP, false)
        builder?.option(LineReader.Option.HISTORY_IGNORE_DUPS, true)
        builder?.option(LineReader.Option.HISTORY_IGNORE_SPACE, true)
        return super.buildReader(builder)
    }

    override fun shutdown() {
        server.shutdown()
    }

    override fun readCommand(): String {
        return ""
    }
}