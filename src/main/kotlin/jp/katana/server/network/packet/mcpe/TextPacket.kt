package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class TextPacket : MinecraftPacket() {
    companion object {
        const val TYPE_RAW: Byte = 0
        const val TYPE_CHAT: Byte = 1
        const val TYPE_TRANSLATION: Byte = 2
        const val TYPE_POPUP: Byte = 3
        const val TYPE_JUKEBOX_POPUP: Byte = 4
        const val TYPE_TIP: Byte = 5
        const val TYPE_SYSTEM: Byte = 6
        const val TYPE_WHISPER: Byte = 7
        const val TYPE_ANNOUNCEMENT: Byte = 8
        const val TYPE_JSON: Byte = 9
    }

    override val packetId: Int = MinecraftProtocols.TEXT_PACKET

    var type = TextPacket.TYPE_RAW
    var needTranslation = false
    var sourceName = ""
    var message = ""
    var parameters = emptyArray<String>()
    var xboxUserId = ""
    var platformChatId = ""

    override fun decodePayload() {
        type = readByte()
        needTranslation = readBoolean()
        when (type) {
            TYPE_CHAT, TYPE_WHISPER, TYPE_ANNOUNCEMENT -> {
                sourceName = readVarString()
                message = readVarString()
            }
            TYPE_RAW, TYPE_TIP, TYPE_SYSTEM, TYPE_JSON -> {
                message = readVarString()
            }
            TYPE_TRANSLATION, TYPE_POPUP, TYPE_JUKEBOX_POPUP -> {
                message = readVarString()
                val count = readUnsignedVarInt()
                for (i in 0 until count) {
                    parameters[i] = readVarString()
                }
            }
        }

        xboxUserId = readVarString()
        platformChatId = readVarString()
    }

    override fun encodePayload() {
        writeByte(type)
        writeBoolean(needTranslation)
        when (type) {
            TYPE_CHAT, TYPE_WHISPER, TYPE_ANNOUNCEMENT -> {
                writeVarString(sourceName)
                writeVarString(message)
            }
            TYPE_RAW, TYPE_TIP, TYPE_SYSTEM, TYPE_JSON -> {
                writeVarString(message)
            }
            TYPE_TRANSLATION, TYPE_POPUP, TYPE_JUKEBOX_POPUP -> {
                writeVarString(message)
                writeUnsignedVarInt(parameters.size)
                for (parameter in parameters) {
                    writeVarString(parameter)
                }
            }
        }

        writeVarString(xboxUserId)
        writeVarString(platformChatId)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }
}