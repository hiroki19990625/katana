package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.actor.PlayerState
import jp.katana.core.data.IResourcePackEntry
import jp.katana.server.actor.ActorPlayer
import jp.katana.server.data.ResourcePackEntry
import jp.katana.server.world.gamerule.BooleanGameRule
import jp.katana.server.world.gamerule.GameRules
import java.util.*

class ResourcePackClientResponsePacket : MinecraftPacket() {
    companion object {
        const val STATUS_REFUSED: Byte = 1
        const val STATUS_SEND_PACKS: Byte = 2
        const val STATUS_HAVE_ALL_PACKS: Byte = 3
        const val STATUS_COMPLETED: Byte = 4
    }

    override val packetId: Int = MinecraftProtocols.RESOURCE_PACK_CLIENT_RESPONSE_PACKET

    var status: Byte = 0
    val packEntries = mutableListOf<IResourcePackEntry>()

    override fun decodePayload() {
        status = readByte()
        val len = readShortLE()
        for (entry in 1..len) {
            val splits = readVarString().split('_')
            packEntries.add(ResourcePackEntry(UUID.fromString(splits[0]), splits[1]))
        }
    }

    override fun encodePayload() {
        writeByte(status.toInt())
        writeShortLE(packEntries.size)
        for (entry in packEntries) {
            writeVarString(entry.uuid.toString() + '_' + entry.version)
        }
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        when (status) {
            STATUS_REFUSED -> {
                player.disconnect("disconnectionScreen.resourcePack")
            }

            STATUS_SEND_PACKS -> {
                for (entry in packEntries) {
                    val pack = server.resourcePackManager.getResourcePack(entry.uuid.toString())
                    if (pack == null) {
                        player.disconnect("disconnectionScreen.resourcePack")
                        return
                    }

                    val resourcePackDataInfoPacket = ResourcePackDataInfoPacket()
                    resourcePackDataInfoPacket.packId = pack.packId
                    resourcePackDataInfoPacket.maxChunkSize = ResourcePackDataInfoPacket.MB_1
                    resourcePackDataInfoPacket.chunkCount =
                        (pack.packSize / resourcePackDataInfoPacket.maxChunkSize).toInt()
                    resourcePackDataInfoPacket.packSize = pack.packSize
                    resourcePackDataInfoPacket.hash = pack.hash

                    player.sendPacket(resourcePackDataInfoPacket)
                }
            }

            STATUS_HAVE_ALL_PACKS -> {
                val resourcePackStackPacket = ResourcePackStackPacket()
                resourcePackStackPacket.mustAccept = server.serverProperties!!.forceResource
                resourcePackStackPacket.resourcePackStack.addAll(server.resourcePackManager.getResourcePacks())

                player.sendPacket(resourcePackStackPacket)
            }

            STATUS_COMPLETED -> {
                startGame(player, server)
            }
        }
    }

    private fun startGame(player: IActorPlayer, server: IServer) {
        if (player is ActorPlayer) {
            val gameRules = GameRules()
            gameRules.put(BooleanGameRule("showcoordinates", true))
            val startGamePacket = StartGamePacket()
            startGamePacket.actorUniqueId = player.uuid.mostSignificantBits
            startGamePacket.actorRuntimeId = player.uuid.leastSignificantBits
            startGamePacket.blockDefinitions = server.defineBlocks
            startGamePacket.itemDefinitions = server.defineItems
            startGamePacket.gameRules = gameRules
            player.sendPacket(startGamePacket)

            val biomeDefinitionListPacket = BiomeDefinitionListPacket()
            biomeDefinitionListPacket.tag = server.defineBiomes.binary()
            player.sendPacket(biomeDefinitionListPacket)

            val availableActorIdentifiersPacket = AvailableActorIdentifiersPacket()
            availableActorIdentifiersPacket.tag = server.defineActors.binary()
            player.sendPacket(availableActorIdentifiersPacket)

            val playStatusPacket = PlayStatusPacket()
            playStatusPacket.status = PlayStatusPacket.PLAYER_SPAWN
            player.sendPacket(playStatusPacket)

            player.state = PlayerState.Spawned
        }
    }
}