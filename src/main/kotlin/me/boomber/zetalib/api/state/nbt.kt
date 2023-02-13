package me.boomber.zetalib.api.state

import kotlinx.serialization.serializer
import me.boomber.zetalib.helper.decode
import me.boomber.zetalib.helper.defaultNbt
import me.boomber.zetalib.helper.encode
import net.minecraft.entity.Entity
import net.minecraft.nbt.NbtCompound
import net.silkmc.silk.nbt.serialization.Nbt
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotations

/**
 * Serialize all properties annotated with [State] to NBT
 */
inline fun <reified T : Entity> saveStateToNbt(entity: T, tag: NbtCompound, nbt: Nbt = defaultNbt) {
    findAnnotatedProperties(T::class, State::class).forEach { prop ->
        val value = prop.get(entity)
        val serializer = nbt.serializersModule.serializer(prop.returnType)
        tag.encode(prop.name, serializer, value, nbt)
    }
}

/**
 * Deserialize all properties annotated with [State] from NBT
 */
inline fun <reified T : Entity> loadStateFromNbt(entity: T, tag: NbtCompound, nbt: Nbt = defaultNbt) {
    findAnnotatedProperties(T::class, State::class)
        .forEach { prop ->
            val serializer = nbt.serializersModule.serializer(prop.returnType)
            val value = tag.decode(prop.name, serializer, nbt)

            if (prop is KMutableProperty1<T, *>) {
                prop.setter.call(entity, value)
            }
        }
}

fun <T : Any> findAnnotatedProperties(obj: KClass<T>, annotation: KClass<out Annotation>): List<KProperty1<T, *>> =
    obj.declaredMemberProperties
        .filter { it.findAnnotations(annotation).isNotEmpty() }

