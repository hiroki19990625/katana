package jp.katana.server.actor.data

import jp.katana.core.IServer
import jp.katana.core.actor.IActor
import jp.katana.core.actor.IActorPlayer
import jp.katana.core.actor.data.IActorData
import jp.katana.core.actor.data.IActorDataManager
import jp.katana.server.network.packet.mcpe.SetActorDataPacket
import java.util.*

class ActorDataManager(val actor: IActor, val server: IServer) : IActorDataManager {
    private val list: MutableMap<Int, IActorData<*>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    override fun <T> getData(id: Int): IActorData<T> {
        return list[id]!! as IActorData<T>
    }

    override fun <T> setData(id: Int, data: IActorData<T>) {
        list[id] = data
    }

    override fun removeData(id: Int) {
        list.remove(id)
    }

    override fun getAllData(): Map<Int, IActorData<*>> {
        return list
    }

    override fun getFlag(id: Int, flagId: Int): Boolean {
        val data = list[id]
        if (data is LongActorData) {
            val array = LongArray(1)
            array[0] = data.value
            val bitSet = BitSet.valueOf(array)
            return bitSet[flagId]
        }

        throw IllegalStateException("invalid flag $id is not LongData.")
    }

    override fun setFlag(id: Int, flagId: Int, value: Boolean) {
        val data = list[id]
        if (data is LongActorData) {
            val array = LongArray(1)
            array[0] = data.value
            val bitSet = BitSet.valueOf(array)
            bitSet[flagId] = value
            data.value = bitSet.toLongArray()[0]
        } else
            throw IllegalStateException("invalid flag $id is not LongData.")
    }

    override fun update() {
        val packet = SetActorDataPacket()
        packet.actorId = actor.uuid.leastSignificantBits
        packet.actorData = this

        if (actor is IActorPlayer) {
            actor.sendPacket(packet)
        } else {
            server.networkManager!!.sendBroadcastPacket(packet)
        }
    }
}