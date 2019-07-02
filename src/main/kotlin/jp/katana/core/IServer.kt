package jp.katana.core

import org.apache.logging.log4j.Logger
import java.io.File

interface IServer {
    val propertiesFile: File
    val state: ServerState
    val logger: Logger

    fun start()
    fun shutdown(): Boolean
    fun shutdownForce(): Boolean
}