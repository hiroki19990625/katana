package jp.katana.core.data

interface ISkin {
    val capeData: ISkinImage
    val skinData: ISkinImage
    val skinGeometry: String
    val skinAnimation: String
    val skinResourcePatch: String
    val skinId: String
    val capeId: String

    val premiumSkin: Boolean
    val personaSkin: Boolean
    val capeOnClassicSkin: Boolean

    val fullSkinId: String
}