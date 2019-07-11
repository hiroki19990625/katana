package jp.katana.core

import jp.katana.core.command.ICommandSender
import jp.katana.core.console.IConsole
import jp.katana.core.event.IEventManager
import jp.katana.core.factory.IFactoryManager
import jp.katana.core.network.INetworkManager
import org.apache.logging.log4j.Logger
import java.io.File
import java.net.InetAddress

/**
 * サーバーを実装します。
 * @property protocolVersion Int
 * @property gameVersion String
 * @property propertiesFile File
 * @property serverProperties IServerProperties?
 * @property state ServerState
 * @property logger Logger
 * @property console IConsole
 * @property factoryManager IFactoryManager
 * @property eventManager IEventManager
 * @property networkManager INetworkManager?
 * @property serverPort Int
 * @property serverAddress InetAddress
 * @property maxPlayer Int
 * @property motd String
 * @property subMotd String
 * @property tickRate Byte
 * @property totalTick Long
 */
interface IServer {
    val protocolVersion: Int
    val gameVersion: String

    val propertiesFile: File
    val serverProperties: IServerProperties?
    val state: ServerState
    val logger: Logger
    val console: IConsole
    val factoryManager: IFactoryManager
    val eventManager: IEventManager
    val networkManager: INetworkManager?

    val serverPort: Int
    val serverAddress: InetAddress
    val maxPlayer: Int
    val motd: String
    val subMotd: String
    val tickRate: Byte
    val totalTick: Long

    fun start()
    fun shutdown(): Boolean
    fun shutdownForce(): Boolean
    fun update(tick: Long): Boolean

    fun executeCommand(sender: ICommandSender, command: String)
}