package jp.katana.server.network.packet.mcpe

import jp.katana.core.IServer
import jp.katana.core.actor.IActorPlayer
import jp.katana.i18n.I18n

class PacketViolationWarningPacket : MinecraftPacket() {
    override val packetId: Int = MinecraftProtocols.PACKET_VIOLATION_WARNING_PACKET;

    var type: PacketViolationType = PacketViolationType.None;
    var severity: PacketViolationSeverity = PacketViolationSeverity.WARNING
    var violationPacketId: Int = 0
    var context: String = ""

    override fun decodePayload() {
        val typeNum = this.readVarInt()
        type = PacketViolationType.values().first { t -> t.type == typeNum }
        severity = PacketViolationSeverity.values()[this.readVarInt()]
        violationPacketId = this.readVarInt()
        context = this.readVarString()
    }

    override fun encodePayload() {
        this.writeVarInt(type.type)
        this.writeVarInt(severity.ordinal)
        this.writeVarInt(this.packetId)
        this.writeVarString(context)
    }

    override fun handleServer(player: IActorPlayer, server: IServer) {
        server.logger.warn(I18n["katana.server.network.packetViolation", violationPacketId, context])
    }

    override fun toString(): String {
        return toPrintString()
    }

    override fun print(builder: StringBuilder, indent: Int) {
        builder.appendIndent("${this.javaClass.simpleName} : 0x${packetId.toString(16)} {\n", indent)
        builder.appendProperty(PacketViolationWarningPacket::type, this, indent + 1)
        builder.appendProperty(PacketViolationWarningPacket::severity, this, indent + 1)
        builder.appendProperty(PacketViolationWarningPacket::violationPacketId, this, indent + 1)
        builder.appendProperty(PacketViolationWarningPacket::context, this, indent + 1)
        builder.appendIndent("}\n", indent)
    }

    enum class PacketViolationType(val type: Int) {
        None(-1),
        MALFORMED_PACKET(0)
    }

    enum class PacketViolationSeverity(val severity: Int) {
        WARNING(0),
        FINAL_WARNING(1),
        TERMINATING_CONNECTION(2)
    }
}