package jp.katana.server.network.packet.mcpe

import jp.katana.server.math.Vector3

class StartGamePacket : MinecraftPacket() {
    companion object {
        const val GAME_PUBLISH_SETTING_NO_MULTI_PLAY = 0
        const val GAME_PUBLISH_SETTING_INVITE_ONLY = 1
        const val GAME_PUBLISH_SETTING_FRIENDS_ONLY = 2
        const val GAME_PUBLISH_SETTING_FRIENDS_OF_FRIENDS = 3
        const val GAME_PUBLISH_SETTING_PUBLIC = 4
    }

    var entityUniqueId: Long = 0
    var entityRuntimeId: Long = 0
    var playerGamemode: Int = 0
    var x: Float = 0.0f
    var y: Float = 0.0f
    var z: Float = 0.0f
    var yaw: Float = 0.0f
    var pitch: Float = 0.0f
    var seed: Int = 0
    var dimension: Byte = 0
    var generator = 1
    var worldGamemode: Int = 0
    var difficulty: Int = 0
    var spawnX: Int = 0
    var spawnY: Int = 0
    var spawnZ: Int = 0
    var hasAchievementsDisabled = true
    var dayCycleStopTime = -1 //-1 = not stopped, any positive value = stopped at that time
    var eduMode = false
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
    var permissionLevel = 1
    var serverChunkTickRange = 4
    var hasLockedBehaviorPack = false
    var hasLockedResourcePack = false
    var isFromLockedWorldTemplate = false
    var isUsingMsaGamertagsOnly = false
    var isFromWorldTemplate = false
    var isWorldTemplateOptionLocked = false
    var isOnlySpawningV1Villagers = false
    var levelId = "" //base64 string, usually the same as world folder name in vanilla
    var worldName: String = ""
    var premiumWorldTemplateId = ""
    var isTrial = false
    var currentTick: Long = 0
    var enchantmentSeed: Int = 0
    var multiplayerCorrelationId = ""

    override val packetId: Int = MinecraftProtocols.START_GAME_PACKET

    override fun decodePayload() {
        writeEntityUniqueId(entityUniqueId)
        writeEntityRuntimeId(entityRuntimeId)

        writeVarInt(playerGamemode)

        writeVector3(Vector3(x.toDouble(), y.toDouble(), z.toDouble()))
        writeFloatLE(yaw.toDouble())
        writeFloatLE(pitch.toDouble())
    }

    override fun encodePayload() {

    }
}