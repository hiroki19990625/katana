package jp.katana.core

/**
 * サーバーのプロパティファイルを実装します。
 * @property allowNethet ネザーの有効化
 * @property allowEnd エンドの有効化
 * @property difficulty ゲームの難易度
 * @property enableCommandBlock コマンドブロックを有効化
 * @property levelName ワールドの名前
 * @property levelSeed ワールドのシード値
 * @property levelType ワールドのタイプ
 * @property maxPlayer 最大プレイヤー数
 * @property motd サーバーのタイトル
 * @property networkCompressionThreshold パケットの圧縮率
 * @property secureMode 通信暗号化の有効化
 * @property playerIdleTimeout プレイヤーのタイムアウト
 * @property pvp PVPの許可
 * @property serverIp サーバーのIPアドレス
 * @property serverPort サーバーのポート
 * @property subMotd サーバーのサブタイトル
 * @property viewDistance チャンクの描画範囲
 * @property whiteList ホワイトリストの有効化
 */
interface IServerProperties {
    var allowNethet: Boolean
    var allowEnd: Boolean
    var difficulty: Byte
    var enableCommandBlock: Boolean
    var levelName: String
    var levelSeed: String
    var levelType: String
    var maxPlayer: Int
    var motd: String
    var networkCompressionThreshold: Byte
    var secureMode: Boolean
    var playerIdleTimeout: Int
    var pvp: Boolean
    var serverIp: String
    var serverPort: Int
    var subMotd: String
    var viewDistance: Short
    var whiteList: Boolean
}