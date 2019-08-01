package jp.katana.core

import jp.katana.core.actor.IActorDefinitions
import jp.katana.core.block.IBlockDefinitions
import jp.katana.core.command.ICommandSender
import jp.katana.core.console.IConsole
import jp.katana.core.event.IEventManager
import jp.katana.core.factory.IFactoryManager
import jp.katana.core.item.IItemDefinitions
import jp.katana.core.network.INetworkManager
import jp.katana.core.resourcepack.IResourcePackManager
import jp.katana.core.world.biome.IBiomeDefinitions
import org.apache.logging.log4j.Logger
import java.io.File
import java.net.InetAddress

/**
 * サーバーを実装します。
 * @property protocolVersion プロトコルのバージョン
 * @property gameVersion ゲームのバージョン
 * @property propertiesFile プロパティファイルの場所
 * @property serverProperties プロパティファイルのインスタンス
 * @property state サーバーのステータス
 * @property logger サーバーのロガー
 * @property console サーバーのコンソール
 * @property factoryManager データファクトリのマネージャー
 * @property eventManager イベントのマネージャー
 * @property networkManager ネットワークのマネージャー
 * @property serverPort サーバーのポート
 * @property serverAddress サーバーのアドレス
 * @property maxPlayer プレイヤーの最大人数
 * @property motd サーバーのタイトル
 * @property subMotd サーバーのサブタイトル
 * @property tickRate サーバーの更新レート
 * @property totalTick サーバーの起動時間
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
    val resourcePackManager: IResourcePackManager

    val serverPort: Int
    val serverAddress: InetAddress
    val maxPlayer: Int
    val motd: String
    val subMotd: String
    val tickRate: Byte
    val totalTick: Long

    val defineBlocks: IBlockDefinitions
    val defineItems: IItemDefinitions
    val defineActors: IActorDefinitions
    val defineBiomes: IBiomeDefinitions

    /**
     * サーバーを開始します。
     */
    fun start()

    /**
     * サーバーを終了します。
     * @return サーバーの終了に成功したかどうか
     */
    fun shutdown(): Boolean

    /**
     * サーバーを強制的に終了します。
     * @return サーバーの強制終了に成功したかどうか
     */
    fun shutdownForce(): Boolean

    /**
     * サーバーの更新時に呼び出される。
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