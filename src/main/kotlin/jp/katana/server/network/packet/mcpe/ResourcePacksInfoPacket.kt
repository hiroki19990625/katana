package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.data.IResourcePackInfo
import jp.katana.server.data.ResourcePackInfo

class ResourcePacksInfoPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.RESOURCE_PACKS_INFO_PACKET

    var mustAccept: Boolean = false
    var unknownBool: Boolean = false
    var behaviourPackEntries = mutableListOf<IResourcePackInfo>()
    var resourcePackEntries = mutableListOf<IResourcePackInfo>()

    override fun decodePayload() {
        mustAccept = readBoolean()
        unknownBool = readBoolean()
        readPacks(behaviourPackEntries)
        readPacks(resourcePackEntries)
    }

    override fun encodePayload() {
        writeBoolean(mustAccept)
        writeBoolean(unknownBool)

        writePacks(behaviourPackEntries)
        writePacks(resourcePackEntries)
    }

    private fun readPacks(list: MutableList<IResourcePackInfo>) {
        val len = readShortLE()
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
        writeShortLE(list.size.toShort())
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
        builder.append("${this.javaClass.simpleName} {\n")
        builder.append("}\n")
    }
}