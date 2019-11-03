package jp.katana.server.data

import jp.katana.core.data.ISkin
import jp.katana.core.data.ISkinImage

class Skin(
    override val capeData: ISkinImage,
    override val skinData: ISkinImage,
    override val skinGeometry: String,
    override val skinAnimation: String,
    override val skinResourcePatch: String,
    override val skinId: String,
    override val capeId: String,
    override val premiumSkin: Boolean,
    override val personaSkin: Boolean,
    override val capeOnClassicSkin: Boolean
) : ISkin {
    override val fullSkinId: String
        get() = skinId + "_" + capeId
}