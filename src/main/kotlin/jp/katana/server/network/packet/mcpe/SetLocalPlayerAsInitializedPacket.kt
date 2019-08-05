package jp.katana.server.network.packet.mcpe

class SetLocalPlayerAsInitializedPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.SET_LOCAL_PLAYER_AS_INITIALIZED_PACKET

    var actorId: Long = 0

    override fun decodePayload() {
        actorId = readActorRuntimeId()
    }

    override fun encodePayload() {
        writeActorRuntimeId(actorId)
    }
}