package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.actor.data.IActorDataManager

class SetActorDataPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.SET_ACTOR_DATA_PACKET

    var actorId: Long = 0
    var actorData: IActorDataManager? = null

    override fun decodePayload() {
        actorId = readActorRuntimeId()
        readActorData(actorData!!)
    }

    override fun encodePayload() {
        writeActorRuntimeId(actorId)
        writeActorData(actorData!!)
    }

    override fun handleServer(player: IActorPlayer, server: IServer) {
        // No cause
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(SetActorDataPacket::actorId, this, indent + 1)
        builder.appendProperty(SetActorDataPacket::actorData, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }
}