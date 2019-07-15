package jp.katana.server.data

import jp.katana.core.data.ISkin

class Skin(
    override val capeData: String,
    override val skinData: String,
    override val skinGeometry: String,
    override val skinGeometryName: String,
    override val skinId: String
) : ISkin