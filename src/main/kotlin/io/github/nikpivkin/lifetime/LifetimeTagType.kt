package io.github.nikpivkin.lifetime

import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

object LifetimeTagType : PersistentDataType<Long, Lifetime> {
    override fun getPrimitiveType() = Long::class.java

    override fun getComplexType() = Lifetime::class.java

    override fun fromPrimitive(
        primitive: Long,
        context: PersistentDataAdapterContext
    ) = Lifetime(primitive)

    override fun toPrimitive(
        complex: Lifetime,
        context: PersistentDataAdapterContext
    ) = complex.value()
}
