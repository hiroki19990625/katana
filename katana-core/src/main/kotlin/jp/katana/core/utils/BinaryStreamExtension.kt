package jp.katana.core.utils

import jp.katana.core.actor.data.*
import jp.katana.core.data.ISkin
import jp.katana.core.data.Skin
import jp.katana.core.data.SkinImage
import jp.katana.core.world.gamerule.IGameRules
import jp.katana.math.Vector3
import jp.katana.math.Vector3Int
import jp.katana.utils.BinaryStream

fun BinaryStream.readGameRules(rules: IGameRules) {
    val len = readUnsignedVarInt()
    for (i in 0 until len) {
        val name = readVarString()
        val type = readByte()
        val rule = rules.getRuleType(name, type)
        rule.read(this)
        rules.put(rule)
    }
}

fun BinaryStream.writeGameRules(rules: IGameRules) {
    writeUnsignedVarInt(rules.size())
    for (rule in rules.getAll()) {
        rule.write(this)
    }
}

fun BinaryStream.readActorData(actorData: IActorDataManager) {
    val c = readUnsignedVarInt()
    for (i in 1..c) {
        val id = readUnsignedVarInt()
        val type = readUnsignedVarInt()
        var data: IActorData<*>? = null
        when (type) {
            IActorData.BYTE -> data = ByteActorData()
            IActorData.SHORT -> data = ShortActorData()
            IActorData.INT -> data = IntActorData()
            IActorData.LONG -> data = LongActorData()
            IActorData.FLOAT -> data = FloatActorData()
            IActorData.NBT -> data = NBTActorData()
            IActorData.STRING -> data = StringActorData()
            IActorData.POS -> data = PositionActorData()
            IActorData.VECTOR3F -> data = Vector3ActorData()
        }

        data!!.read(this)
        actorData.setData(id, data)
    }
}

fun BinaryStream.writeActorData(actorData: IActorDataManager) {
    val map = actorData.getAllData();
    writeUnsignedVarInt(map.size)
    for (pair in map) {
        writeUnsignedVarInt(pair.key)
        writeUnsignedVarInt(pair.value.type)

        pair.value.write(this)
    }
}

fun BinaryStream.readSkin(): ISkin {
    val skinId = readVarString()
    val resource = readVarString()
    val skinDataW = readIntLE()
    val skinDataH = readIntLE()
    val skinData = String(read(readUnsignedVarInt()))
    val capeDataW = readIntLE()
    val capeDataH = readIntLE()
    val capeData = String(read(readUnsignedVarInt()))
    readIntLE()
    val geometryData = readVarString()
    val animationData = readVarString()
    val premiumSkin = readBoolean()
    val personaSkin = readBoolean()
    val capeOnClassicSkin = readBoolean()
    val capeId = readVarString()

    return Skin(
        SkinImage(skinDataW, skinDataH, skinData),
        SkinImage(capeDataW, capeDataH, capeData),
        geometryData,
        animationData,
        resource,
        skinId,
        capeId,
        premiumSkin,
        personaSkin,
        capeOnClassicSkin
    )
}

fun BinaryStream.writeSkin(skin: ISkin) {
    writeVarString(skin.skinId)
    writeVarString(skin.skinResourcePatch)

    val skinData = skin.skinData.data.toByteArray()
    writeIntLE(skin.skinData.width)
    writeIntLE(skin.skinData.height)
    writeUnsignedVarInt(skinData.size)
    write(skinData)

    val capeData = skin.capeData.data.toByteArray()
    writeIntLE(skin.capeData.width)
    writeIntLE(skin.capeData.height)
    writeUnsignedVarInt(capeData.size)
    write(capeData)

    writeIntLE(0)
    //TODO: Animation

    writeVarString(skin.skinGeometry)
    writeVarString(skin.skinAnimation)

    writeBoolean(skin.premiumSkin)
    writeBoolean(skin.personaSkin)
    writeBoolean(skin.capeOnClassicSkin)

    writeVarString(skin.capeId)
    writeVarString(skin.fullSkinId)
}

fun BinaryStream.readBlockPosition(): Vector3Int {
    return Vector3Int(readVarInt(), readUnsignedVarInt(), readVarInt())
}

fun BinaryStream.writeBlockPosition(pos: Vector3Int) {
    writeVarInt(pos.x)
    writeUnsignedVarInt(pos.y)
    writeVarInt(pos.z)
}

fun BinaryStream.readUnsignedBlockPosition(): Vector3Int {
    return Vector3Int(readVarInt(), readVarInt(), readVarInt())
}

fun BinaryStream.writeUnsignedBlockPosition(pos: Vector3Int) {
    writeVarInt(pos.x)
    writeVarInt(pos.y)
    writeVarInt(pos.z)
}

fun BinaryStream.readVector3(): Vector3 {
    return Vector3(readFloatLE().toDouble(), readFloatLE().toDouble(), readFloatLE().toDouble())
}

fun BinaryStream.writeVector3(pos: Vector3) {
    writeFloatLE(pos.x.toFloat())
    writeFloatLE(pos.y.toFloat())
    writeFloatLE(pos.z.toFloat())
}

fun BinaryStream.readActorUniqueId(): Long {
    return readVarLong()
}

fun BinaryStream.writeActorUniqueId(id: Long) {
    writeVarLong(id)
}