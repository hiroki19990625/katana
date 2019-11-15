package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.data.IResourcePackInfo
import jp.katana.server.Server
import jp.katana.server.data.ResourcePackInfo


class ResourcePackStackPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.RESOURCE_PACK_STACK_PACKET

    var mustAccept = false
    var behaviourPackStack = mutableListOf<IResourcePackInfo>()
    var resourcePackStack = mutableListOf<IResourcePackInfo>()
    var gameVersion = Server.GAME_VERSION

    override fun decodePayload() {
        mustAccept = readBoolean()

        readPacks(behaviourPackStack)
        readPacks(resourcePackStack)

        gameVersion = readVarString()
    }

    override fun encodePayload() {
        writeBoolean(mustAccept)

        writePacks(behaviourPackStack)
        writePacks(resourcePackStack)

        writeVarString(gameVersion)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }

    private fun readPacks(list: MutableList<IResourcePackInfo>) {
        val len = readUnsignedVarInt()
        for (pack in 1..len) {
            list.add(
                ResourcePackInfo(
                    null,
                    readVarString(),
                    readVarString(),
                    0,
                    "",
                    readVarString(),
                    "",
                    false,
                    ByteArray(0)
                )
            )
        }
    }

    private fun writePacks(list: MutableList<IResourcePackInfo>) {
        writeUnsignedInt(list.size.toLong())
        for (pack in list) {
            writeVarString(pack.packId)
            writeVarString(pack.packVersion)
            writeVarString(pack.subPackName)
        }
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(ResourcePackStackPacket::mustAccept, this, indent + 1)
        builder.appendListProperty(ResourcePackStackPacket::behaviourPackStack, this, indent + 1)
        builder.appendListProperty(ResourcePackStackPacket::resourcePackStack, this, indent + 1)
        builder.appendProperty(ResourcePackStackPacket::gameVersion, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}