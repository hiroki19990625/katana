package jp.katana.server.console

import jp.katana.core.ServerState
import jp.katana.core.console.IConsole
import jp.katana.server.Server
import net.minecrell.terminalconsole.SimpleTerminalConsole
import org.jline.reader.LineReader
import org.jline.reader.LineReaderBuilder
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class KatanaConsole(private val server: Server) : SimpleTerminalConsole(), IConsole {
    private val maxCommandQueue = 20
    private val commandQueue: BlockingQueue<String> = LinkedBlockingQueue()

    override fun isRunning(): Boolean {
        return server.state == ServerState.Running
    }

    override fun runCommand(command: String?) {
        if (!command.isNullOrBlank()/* && commandQueue.size > 20*/)
            commandQueue.put(command)
        /*else
            server.logger.info(I18n["katana.server.command.generic.tooManyRequests"])*/
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

    override fun readCommand(): String? {
        if (commandQueue.isNotEmpty())
            return commandQueue.take()

        return null
    }
}