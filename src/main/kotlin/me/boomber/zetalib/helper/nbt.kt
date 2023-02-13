package me.boomber.zetalib.helper

import kotlinx.serialization.KSerializer
import net.minecraft.nbt.NbtCompound
import net.silkmc.silk.nbt.serialization.Nbt
import net.silkmc.silk.nbt.serialization.decodeFromNbtElement
import net.silkmc.silk.nbt.serialization.encodeToNbtElement

val defaultNbt = Nbt { ignoreUnknownKeys = true }

inline fun <reified T : Any> NbtCompound.encode(key: String, value: T, nbt: Nbt = defaultNbt) {
    put(key, nbt.encodeToNbtElement(value))
}

inline fun <reified T : Any> NbtCompound.decode(key: String, nbt: Nbt = defaultNbt): T? {
    val data = get(key) ?: return null
    return nbt.decodeFromNbtElement<T>(data)
}

fun <T> NbtCompound.encode(key: String, serializer: KSerializer<T>, value: T, nbt: Nbt = defaultNbt) {
    put(key, nbt.encodeToNbtElement(serializer, value))
}

fun <T> NbtCompound.decode(key: String, serializer: KSerializer<T>, nbt: Nbt = defaultNbt): T? {
    val data = get(key) ?: return null
    return nbt.decodeFromNbtElement(serializer, data)
}
