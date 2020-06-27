package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.block.IBlockDefinitions
import jp.katana.core.item.IItemDefinitions
import jp.katana.core.world.gamerule.IGameRules
import jp.katana.math.Vector3
import jp.katana.math.Vector3Int
import jp.katana.server.Server

class StartGamePacket : MinecraftPacket() {
    companion object {
        const val GAME_PUBLISH_SETTING_NO_MULTI_PLAY = 0
        const val GAME_PUBLISH_SETTING_INVITE_ONLY = 1
        const val GAME_PUBLISH_SETTING_FRIENDS_ONLY = 2
        const val GAME_PUBLISH_SETTING_FRIENDS_OF_FRIENDS = 3
        const val GAME_PUBLISH_SETTING_PUBLIC = 4
    }

    var actorUniqueId: Long = 0
    var actorRuntimeId: Long = 0
    var playerGamemode: Int = 0
    var x: Float = 0.0f
    var y: Float = 0.0f
    var z: Float = 0.0f
    var yaw: Float = 0.0f
    var pitch: Float = 0.0f
    var seed: Int = 0
    var dimension: Int = 0
    var generator: Int = 1
    var worldGamemode: Int = 0
    var difficulty: Int = 0
    var spawnX: Int = 0
    var spawnY: Int = 0
    var spawnZ: Int = 0
    var hasAchievementsDisabled = true
    var dayCycleStopTime = -1 //-1 = not stopped, any positive value = stopped at that time
    var eduEditionOffer = 0
    var hasEduFeaturesEnabled = false
    var rainLevel: Float = 0.0f
    var lightningLevel: Float = 0.0f
    var hasConfirmedPlatformLockedContent = false
    var multiplayerGame = true
    var broadcastToLAN = true
    var xblBroadcastIntent = GAME_PUBLISH_SETTING_PUBLIC
    var platformBroadcastIntent = GAME_PUBLISH_SETTING_PUBLIC
    var commandsEnabled: Boolean = false
    var isTexturePacksRequired = false
    var bonusChest = false
    var hasStartWithMapEnabled = false
    var trustingPlayers = false
    var permissionLevel = 1
    var serverChunkTickRange = 4
    var hasLockedBehaviorPack = false
    var hasLockedResourcePack = false
    var isFromLockedWorldTemplate = false
    var isUsingMsaGamertagsOnly = false
    var isFromWorldTemplate = false
    var isWorldTemplateOptionLocked = false
    var isOnlySpawningV1Villagers = false
    var vanillaVersion = Server.GAME_VERSION
    var levelId = "" //base64 string, usually the same as world folder name in vanilla
    var worldName: String = "World"
    var premiumWorldTemplateId = ""
    var isTrial = false
    var isMovementServerAuthoritative = false
    var isInventoryServerAuthoritative = false
    var currentTick: Long = 0
    var enchantmentSeed: Int = 0

    var gameRules: IGameRules? = null

    var blockDefinitions: IBlockDefinitions? = null
    var itemDefinitions: IItemDefinitions? = null

    var multiplayerCorrelationId = ""

    override val packetId: Int = MinecraftProtocols.START_GAME_PACKET

    override fun decodePayload() {

    }

    override fun encodePayload() {
        writeActorUniqueId(actorUniqueId)
        writeActorRuntimeId(actorRuntimeId)

        writeVarInt(playerGamemode)

        writeVector3(Vector3(x.toDouble(), y.toDouble(), z.toDouble()))
        writeFloatLE(yaw)
        writeFloatLE(pitch)

        writeVarInt(seed)
        writeShortLE(0)
        writeVarString("")
        writeVarInt(dimension)
        writeVarInt(generator)
        writeVarInt(worldGamemode)
        writeVarInt(difficulty)

        writeBlockPosition(Vector3Int(spawnX, spawnY, spawnZ))

        writeBoolean(hasAchievementsDisabled)

        writeVarInt(dayCycleStopTime)

        writeVarInt(eduEditionOffer)
        writeBoolean(hasEduFeaturesEnabled)

        writeVarString("")

        writeFloatLE(rainLevel)
        writeFloatLE(lightningLevel)

        writeBoolean(hasConfirmedPlatformLockedContent)
        writeBoolean(multiplayerGame)
        writeBoolean(broadcastToLAN)

        writeVarInt(xblBroadcastIntent)
        writeVarInt(platformBroadcastIntent)

        writeBoolean(commandsEnabled)

        writeBoolean(isTexturePacksRequired)

        if (gameRules == null)
            writeUnsignedVarInt(0)
        else {
            writeGameRules(gameRules!!)
        }

        writeBoolean(bonusChest)
        writeBoolean(hasStartWithMapEnabled)

        writeVarInt(permissionLevel)

        writeIntLE(serverChunkTickRange)

        writeBoolean(hasLockedBehaviorPack)
        writeBoolean(hasLockedResourcePack)

        writeBoolean(isFromLockedWorldTemplate)
        writeBoolean(isUsingMsaGamertagsOnly)
        writeBoolean(isFromWorldTemplate)
        writeBoolean(isWorldTemplateOptionLocked)
        writeBoolean(isOnlySpawningV1Villagers)
        writeVarString(vanillaVersion)
        writeIntLE(0)
        writeIntLE(0)
        writeBoolean(false)
        writeBoolean(false)

        writeVarString(levelId)
        writeVarString(worldName)
        writeVarString(premiumWorldTemplateId)

        writeBoolean(isTrial)
        writeBoolean(isMovementServerAuthoritative)

        writeLongLE(currentTick)

        writeVarInt(enchantmentSeed)

        write(blockDefinitions!!.binary())

        if (itemDefinitions == null)
            writeUnsignedVarInt(0)
        else {
            writeUnsignedVarInt(itemDefinitions!!.size())
            write(itemDefinitions!!.binary())
        }

        writeVarString(multiplayerCorrelationId)
        writeBoolean(isInventoryServerAuthoritative)
    }

    override fun handleServer(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(StartGamePacket::actorUniqueId, this, indent + 1)
        builder.appendProperty(StartGamePacket::actorRuntimeId, this, indent + 1)
        builder.appendProperty(StartGamePacket::playerGamemode, this, indent + 1)
        builder.appendProperty(StartGamePacket::x, this, indent + 1)
        builder.appendProperty(StartGamePacket::y, this, indent + 1)
        builder.appendProperty(StartGamePacket::z, this, indent + 1)
        builder.appendProperty(StartGamePacket::yaw, this, indent + 1)
        builder.appendProperty(StartGamePacket::pitch, this, indent + 1)
        builder.appendProperty(StartGamePacket::seed, this, indent + 1)
        builder.appendProperty(StartGamePacket::dimension, this, indent + 1)
        builder.appendProperty(StartGamePacket::generator, this, indent + 1)
        builder.appendProperty(StartGamePacket::worldGamemode, this, indent + 1)
        builder.appendProperty(StartGamePacket::difficulty, this, indent + 1)
        builder.appendProperty(StartGamePacket::spawnX, this, indent + 1)
        builder.appendProperty(StartGamePacket::spawnY, this, indent + 1)
        builder.appendProperty(StartGamePacket::spawnZ, this, indent + 1)
        builder.appendProperty(StartGamePacket::hasAchievementsDisabled, this, indent + 1)
        builder.appendProperty(StartGamePacket::dayCycleStopTime, this, indent + 1)
        builder.appendProperty(StartGamePacket::eduEditionOffer, this, indent + 1)
        builder.appendProperty(StartGamePacket::hasEduFeaturesEnabled, this, indent + 1)
        builder.appendProperty(StartGamePacket::rainLevel, this, indent + 1)
        builder.appendProperty(StartGamePacket::lightningLevel, this, indent + 1)
        builder.appendProperty(StartGamePacket::hasConfirmedPlatformLockedContent, this, indent + 1)
        builder.appendProperty(StartGamePacket::multiplayerGame, this, indent + 1)
        builder.appendProperty(StartGamePacket::broadcastToLAN, this, indent + 1)
        builder.appendProperty(StartGamePacket::xblBroadcastIntent, this, indent + 1)
        builder.appendProperty(StartGamePacket::platformBroadcastIntent, this, indent + 1)
        builder.appendProperty(StartGamePacket::commandsEnabled, this, indent + 1)
        builder.appendProperty(StartGamePacket::isTexturePacksRequired, this, indent + 1)
        builder.appendProperty(StartGamePacket::gameRules, this, indent + 1)
        builder.appendProperty(StartGamePacket::bonusChest, this, indent + 1)
        builder.appendProperty(StartGamePacket::hasStartWithMapEnabled, this, indent + 1)
        builder.appendProperty(StartGamePacket::permissionLevel, this, indent + 1)
        builder.appendProperty(StartGamePacket::serverChunkTickRange, this, indent + 1)
        builder.appendProperty(StartGamePacket::hasLockedBehaviorPack, this, indent + 1)
        builder.appendProperty(StartGamePacket::hasLockedResourcePack, this, indent + 1)
        builder.appendProperty(StartGamePacket::isFromLockedWorldTemplate, this, indent + 1)
        builder.appendProperty(StartGamePacket::isUsingMsaGamertagsOnly, this, indent + 1)
        builder.appendProperty(StartGamePacket::isFromWorldTemplate, this, indent + 1)
        builder.appendProperty(StartGamePacket::isWorldTemplateOptionLocked, this, indent + 1)
        builder.appendProperty(StartGamePacket::isOnlySpawningV1Villagers, this, indent + 1)
        builder.appendProperty(StartGamePacket::vanillaVersion, this, indent + 1)
        builder.appendProperty(StartGamePacket::levelId, this, indent + 1)
        builder.appendProperty(StartGamePacket::worldName, this, indent + 1)
        builder.appendProperty(StartGamePacket::premiumWorldTemplateId, this, indent + 1)
        builder.appendProperty(StartGamePacket::isTrial, this, indent + 1)
        builder.appendProperty(StartGamePacket::isMovementServerAuthoritative, this, indent + 1)
        builder.appendProperty(StartGamePacket::currentTick, this, indent + 1)
        builder.appendProperty(StartGamePacket::enchantmentSeed, this, indent + 1)
        builder.appendProperty(StartGamePacket::blockDefinitions, this, indent + 1)
        builder.appendProperty(StartGamePacket::itemDefinitions, this, indent + 1)
        builder.appendProperty(StartGamePacket::multiplayerCorrelationId, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}