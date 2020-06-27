package jp.katana.server.actor.attribute

import jp.katana.core.IServer
import jp.katana.core.actor.IActor
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.actor.attribute.IActorAttribute
import jp.katana.core.actor.attribute.IActorAttributes
import jp.katana.server.network.packet.mcpe.UpdateAttributesPacket

class ActorAttributes(val actor: IActor, val server: IServer) : IActorAttributes {
    companion object {
        val ABSORPTION =
            ActorAttribute("minecraft:absorption", 340282346638528859811704183484516925440.0f, 0f, 0f, 0f)
        val SATURATION =
            ActorAttribute("minecraft:player.saturation", 20.0f, 0f, 5.0f, 5.0f)
        val EXHAUSTION =
            ActorAttribute("minecraft:player.exhaustion", 4.0f, 0f, 0.41f, 0.41f)
        val KNOCK_BACK_RESISTANCE =
            ActorAttribute("minecraft:knockback_resistance", 1.0f, 0.0f, 0.0f, 0.0f)
        val HEALTH =
            ActorAttribute("minecraft:health", 20.0f, 0.0f, 20.0f, 20.0f)
        val MOVEMENT_SPEED =
            ActorAttribute("minecraft:movement", 340282346638528859811704183484516925440.0f, 0.0f, 0.10f, 0.10f)
        val FOLLOW_RANGE =
            ActorAttribute("minecraft:follow_range", 2048.0f, 0.0f, 16.0f, 16.0f)
        val HUNGER =
            ActorAttribute("minecraft:player.hunger", 20.0f, 0.0f, 20.0f, 20.0f)
        val ATTACK_DAMAGE =
            ActorAttribute("minecraft:attack_damage", 340282346638528859811704183484516925440.0f, 0.0f, 1.0f, 1.0f)
        val EXPERIENCE_LEVEL =
            ActorAttribute("minecraft:player.level", 24791.0f, 0.0f, 0.0f, 0.0f)
        val EXPERIENCE =
            ActorAttribute("minecraft:player.experience", 1.0f, 0.0f, 0.0f, 0.0f)
        val LUCK =
            ActorAttribute("minecraft:luck", 1024.0f, 0.0f, 0.0f, 0.0f)
        val FALL_DAMAGE =
            ActorAttribute("minecraft:fall_damage", 340282346638528859811704183484516925440.0f, 0.0f, 1.0f, 1.0f)
    }

    private val attributes: MutableMap<String, IActorAttribute> = mutableMapOf()

    override fun setAttribute(attribute: IActorAttribute) {
        attributes[attribute.name] = attribute
    }

    override fun getAttribute(name: String): IActorAttribute {
        return attributes[name]!!
    }

    override fun removeAttribute(name: String) {
        attributes.remove(name)
    }

    override fun getAttributes(): List<IActorAttribute> {
        return attributes.values.toList()
    }

    override fun update() {
        val packet = UpdateAttributesPacket()
        packet.actorId = actor.uuid.leastSignificantBits
        packet.attributes = this

        if (actor is IActorPlayer) {
            actor.sendPacket(packet)
        } else {
            server.networkManager!!.sendBroadcastPacket(packet)
        }
    }
}