package me.boomber.zetalib.api.state

import kotlinx.serialization.serializer
import me.boomber.zetalib.helper.decode
import me.boomber.zetalib.helper.encode
import net.minecraft.nbt.NbtCompound
import net.silkmc.silk.nbt.serialization.Nbt
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

private fun getName(prop: KProperty1<*, *>, state: State) =
    when (state.name) {
        "" -> prop.name
        else -> state.name
    }

internal fun <T, V> loadStateFromNbt(receiver: T, prop: KProperty1<out T, V>, state: State, tag: NbtCompound, nbt: Nbt = Nbt) {
    if (prop !is KMutableProperty1<*, *>)
        return

    val serializer = nbt.serializersModule.serializer(prop.returnType)
    val value = tag.decode(getName(prop, state), serializer, nbt)

    prop.setter.call(receiver, value)
}


internal fun <T, V> saveStateToNbt(receiver: T, prop: KProperty1<out T, V>, state: State, tag: NbtCompound, nbt: Nbt = Nbt) {
    val value = prop.getter.call(receiver)
    val serializer = nbt.serializersModule.serializer(prop.returnType)
    tag.encode(getName(prop, state), serializer, value, nbt)
}
