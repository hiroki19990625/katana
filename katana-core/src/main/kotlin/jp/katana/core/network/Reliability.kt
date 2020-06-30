package jp.katana.core.network

/**
 * パケットの信頼性を提供します。
 * @property id パケットの信頼性ID
 * @constructor
 */
enum class Reliability(val id: Byte) {
    UNRELIABLE(0),
    UNRELIABLE_SEQUENCED(1),
    RELIABLE(2),
    RELIABLE_ORDERED(3),
    RELIABLE_SEQUENCED(4),
    UNRELIABLE_WITH_ACK_RECEIPT(5),
    RELIABLE_WITH_ACK_RECEIPT(6),
    RELIABLE_ORDERED_WITH_ACK_RECEIPT(7);
}