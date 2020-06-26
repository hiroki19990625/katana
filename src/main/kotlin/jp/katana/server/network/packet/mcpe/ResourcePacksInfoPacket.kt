package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.data.IResourcePackInfo
import jp.katana.server.data.ResourcePackInfo

class ResourcePacksInfoPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.RESOURCE_PACKS_INFO_PACKET

    var mustAccept: Boolean = false
    var containsScript: Boolean = false
    var behaviourPackEntries = mutableListOf<IResourcePackInfo>()
    var resourcePackEntries = mutableListOf<IResourcePackInfo>()

    override fun decodePayload() {
        mustAccept = readBoolean()
        containsScript = readBoolean()
        readPacks(behaviourPackEntries)
        readPacks(resourcePackEntries)
    }

    override fun encodePayload() {
        writeBoolean(mustAccept)
        writeBoolean(containsScript)

        writePacks(behaviourPackEntries)
        writePacks(resourcePackEntries)
    }

    private fun readPacks(list: MutableList<IResourcePackInfo>) {
        val len = readUnsignedShortLE()
        for (i in 1..len) {
            list.add(
                ResourcePackInfo(
                    null,
                    readVarString(),
                    readVarString(),
                    readLongLE(),
                    readVarString(),
                    readVarString(),
                    readVarString(),
                    readBoolean(),
                    ByteArray(0)
                )
            )
        }
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }

    private fun writePacks(list: MutableList<IResourcePackInfo>) {
        writeUnsignedShortLE(list.size)
        for (pack in list) {
            writeVarString(pack.packId)
            writeVarString(pack.packVersion)
            writeLongLE(pack.packSize)

            writeVarString(pack.encryptionKey)
            writeVarString(pack.subPackName)
            writeVarString(pack.contentIdentity)

            writeBoolean(pack.unknownBool)
        }
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(ResourcePacksInfoPacket::mustAccept, this, indent + 1)
        builder.appendProperty(ResourcePacksInfoPacket::containsScript, this, indent + 1)
        builder.appendListProperty(ResourcePacksInfoPacket::behaviourPackEntries, this, indent + 1)
        builder.appendListProperty(ResourcePacksInfoPacket::resourcePackEntries, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}