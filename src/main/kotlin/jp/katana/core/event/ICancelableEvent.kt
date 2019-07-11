package jp.katana.core.event

/**
 * キャンセル可能なイベントのデータを実装します。
 * @property isCancel キャンセルするかどうか
 */
interface ICancelableEvent : IEvent {
    var isCancel: Boolean
}