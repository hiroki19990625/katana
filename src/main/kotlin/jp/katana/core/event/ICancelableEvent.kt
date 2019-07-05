package jp.katana.core.event

interface ICancelableEvent : IEvent {
    var isCancel: Boolean
}