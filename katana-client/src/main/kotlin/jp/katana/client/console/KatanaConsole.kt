package jp.katana.client.console

import jp.katana.client.Client
import jp.katana.core.ClientState
import jp.katana.core.ServerState
import jp.katana.core.console.IConsole
import net.minecrell.terminalconsole.SimpleTerminalConsole
import org.jline.reader.LineReader
import org.jline.reader.LineReaderBuilder
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class KatanaConsole(private val client: Client) : SimpleTerminalConsole(), IConsole {
    private val maxCommandQueue = 20
    private val commandQueue: BlockingQueue<String> = LinkedBlockingQueue()

    override fun isRunning(): Boolean {
        return client.state == ClientState.Running
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
        if (client.state == ClientState.Running) {
            client.shutdown()
        }
    }

    override fun readCommand(): String? {
        if (commandQueue.isNotEmpty())
            return commandQueue.take()

        return null
    }
}