package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.debug.appendPropertyBufferNetworkNBT
import jp.katana.debug.appendIndent

class BiomeDefinitionListPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.BIOME_DEFINITION_LIST_PACKET

    var tag: ByteArray = ByteArray(0)

    override fun decodePayload() {
        tag = readRemaining()
    }

    override fun encodePayload() {
        write(tag)
    }

    override fun handleServer(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendPropertyBufferNetworkNBT(BiomeDefinitionListPacket::tag, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}