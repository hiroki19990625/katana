package jp.katana.core

import jp.katana.core.console.IConsole
import org.apache.logging.log4j.Logger
import java.io.File

interface IServer {
    val propertiesFile: File
    val serverProperties: IServerProperties?
    val state: ServerState
    val logger: Logger
    val console: IConsole

    fun start()
    fun shutdown(): Boolean
    fun shutdownForce(): Boolean
}