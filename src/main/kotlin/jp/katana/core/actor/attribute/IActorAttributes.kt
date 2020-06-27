package jp.katana.core.actor.attribute

interface IActorAttributes {
    fun setAttribute(attribute: IActorAttribute)
    fun getAttribute(name: String): IActorAttribute
    fun removeAttribute(name: String)

    fun getAttributes(): List<IActorAttribute>

    fun update()
}