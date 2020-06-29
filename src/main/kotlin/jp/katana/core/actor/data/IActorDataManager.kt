package jp.katana.core.actor.data

interface IActorDataManager {
    fun <T> getData(id: Int): IActorData<T>
    fun <T> setData(id: Int, data: IActorData<T>)
    fun removeData(id: Int)

    fun getFlag(id: Int, flagId: Int): Boolean
    fun setFlag(id: Int, flagId: Int, value: Boolean = true)

    fun getAllData(): Map<Int, IActorData<*>>

    fun update()
}