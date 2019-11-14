package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer

class BiomeDefinitionListPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.BIOME_DEFINITION_LIST_PACKET

    var tag: ByteArray = ByteArray(0)

    override fun decodePayload() {
        tag = readRemaining()
    }

    override fun encodePayload() {
        write(tag)
    }

    override fun handle(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.append("${this.javaClass.simpleName} {\n")
        builder.appendPropertyBufferNetworkNBT(BiomeDefinitionListPacket::tag, this, indent + 1)
        builder.append("}\n")
    }
}