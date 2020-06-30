package jp.katana.core

import jp.katana.core.command.ICommandSender
import jp.katana.core.console.IConsole
import jp.katana.core.event.IEventManager
import jp.katana.factory.IFactoryManager
import org.apache.logging.log4j.Logger
import java.io.File
import java.net.InetAddress

interface IClient {
    val protocolVersion: Int
    val gameVersion: String

    val propertiesFile: File
    val clientProperties: IClientProperties?
    val state: ClientState
    val logger: Logger
    val console: IConsole

    val factoryManager: IFactoryManager
    val eventManager: IEventManager

    val serverPort: Int
    val serverAddress: InetAddress

    val tickRate: Byte
    val totalTick: Long

    /**
     * クライアントを開始します。
     */
    fun start()

    /**
     * クライアントを終了します。
     * @return サーバーの終了に成功したかどうか
     */
    fun shutdown(): Boolean

    /**
     * クライアントを強制的に終了します。
     * @return サーバーの強制終了に成功したかどうか
     */
    fun shutdownForce(): Boolean

    /**
     * クライアントの更新時に呼び出される。
     * @param tick これまでの累計時間
     * @return 更新に成功したかどうか
     */
    fun update(tick: Long): Boolean

    /**
     * コマンドを実行した場合に呼び出される。
     * @param sender コマンド実行者
     * @param command コマンド文字列
     */
    fun executeCommand(sender: ICommandSender, command: String)
}