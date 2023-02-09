package me.boomber.zetalib.serialization

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minecraft.nbt.*
import net.minecraft.nbt.visitor.StringNbtWriter
import kotlin.reflect.KClass
import kotlin.reflect.cast

@Serializable
sealed interface NbtElementSurrogate

@Serializable
class NbtStringSurrogate(val value: String) : NbtElementSurrogate

@Serializable
class NbtByteSurrogate(val value: Byte) : NbtElementSurrogate

@Serializable
class NbtShortSurrogate(val value: Short) : NbtElementSurrogate

@Serializable
class NbtIntSurrogate(val value: Int) : NbtElementSurrogate

@Serializable
class NbtLongSurrogate(val value: Long) : NbtElementSurrogate

@Serializable
class NbtFloatSurrogate(val value: Float) : NbtElementSurrogate

@Serializable
class NbtDoubleSurrogate(val value: Double) : NbtElementSurrogate

@Serializable
class NbtByteArraySurrogate(val value: ByteArray) : NbtElementSurrogate

@Serializable
class NbtIntArraySurrogate(val value: IntArray) : NbtElementSurrogate

@Serializable
class NbtLongArraySurrogate(val value: LongArray) : NbtElementSurrogate

@Serializable
class NbtListSurrogate(val list: List<NbtElementSurrogate>) : NbtElementSurrogate

@Serializable
class NbtCompoundSurrogate(val entries: Map<String, NbtElementSurrogate>) : NbtElementSurrogate

@Serializable
object NbtEndSurrogate : NbtElementSurrogate

class KNbtElementSerializer : SurrogateSerializer<NbtElement, NbtElementSurrogate>() {
    override val delegate: KSerializer<NbtElementSurrogate> = NbtElementSurrogate.serializer()

    override fun decode(value: NbtElementSurrogate): NbtElement = when (value) {
        is NbtStringSurrogate -> NbtString.of(value.value)
        is NbtByteSurrogate -> NbtByte.of(value.value)
        is NbtShortSurrogate -> NbtShort.of(value.value)
        is NbtIntSurrogate -> NbtInt.of(value.value)
        is NbtLongSurrogate -> NbtLong.of(value.value)
        is NbtFloatSurrogate -> NbtFloat.of(value.value)
        is NbtDoubleSurrogate -> NbtDouble.of(value.value)
        is NbtByteArraySurrogate -> NbtByteArray(value.value)
        is NbtIntArraySurrogate -> NbtIntArray(value.value)
        is NbtLongArraySurrogate -> NbtLongArray(value.value)
        is NbtListSurrogate -> NbtList().apply {
            addAll(value.list.map(::decode))
        }
        is NbtCompoundSurrogate -> NbtCompound().apply {
            value.entries.forEach { (key, value) ->
                put(key, decode(value))
            }
        }
        is NbtEndSurrogate -> NbtEnd.INSTANCE
    }

    override fun encode(value: NbtElement): NbtElementSurrogate = when (value) {
        is NbtString -> NbtStringSurrogate(value.asString())
        is NbtByte -> NbtByteSurrogate(value.byteValue())
        is NbtShort -> NbtShortSurrogate(value.shortValue())
        is NbtInt -> NbtIntSurrogate(value.intValue())
        is NbtLong -> NbtLongSurrogate(value.longValue())
        is NbtFloat -> NbtFloatSurrogate(value.floatValue())
        is NbtDouble -> NbtDoubleSurrogate(value.doubleValue())
        is NbtByteArray -> NbtByteArraySurrogate(value.byteArray)
        is NbtIntArray -> NbtIntArraySurrogate(value.intArray)
        is NbtLongArray -> NbtLongArraySurrogate(value.longArray)
        is NbtList -> NbtListSurrogate(value.map(::encode))
        is NbtCompound -> NbtCompoundSurrogate(
            buildMap {
                for (key in value.keys) {
                    val content = value[key] ?: continue
                    put(key, encode(content))
                }
            }
        )
        is NbtEnd -> NbtEndSurrogate
        else -> throw IllegalArgumentException("Unknown NbtElement type: ${value::class}")
    }
}

class KNbtStringSerializer : NbtSurrogateSerializer<NbtString, NbtStringSurrogate>("NbtString", NbtString::class)
class KNbtByteSerializer : NbtSurrogateSerializer<NbtByte, NbtByteSurrogate>("NbtByte", NbtByte::class)
class KNbtShortSerializer : NbtSurrogateSerializer<NbtShort, NbtShortSurrogate>("NbtShort", NbtShort::class)
class KNbtIntSerializer : NbtSurrogateSerializer<NbtInt, NbtIntSurrogate>("NbtInt", NbtInt::class)
class KNbtLongSerializer : NbtSurrogateSerializer<NbtLong, NbtLongSurrogate>("NbtLong", NbtLong::class)
class KNbtFloatSerializer : NbtSurrogateSerializer<NbtFloat, NbtFloatSurrogate>("NbtFloat", NbtFloat::class)
class KNbtDoubleSerializer : NbtSurrogateSerializer<NbtDouble, NbtDoubleSurrogate>("NbtDouble", NbtDouble::class)
class KNbtByteArraySerializer : NbtSurrogateSerializer<NbtByteArray, NbtByteArraySurrogate>("NbtByteArray", NbtByteArray::class)
class KNbtIntArraySerializer : NbtSurrogateSerializer<NbtIntArray, NbtIntArraySurrogate>("NbtIntArray", NbtIntArray::class)
class KNbtLongArraySerializer : NbtSurrogateSerializer<NbtLongArray, NbtLongArraySurrogate>("NbtLongArray", NbtLongArray::class)
class KNbtListSerializer : NbtSurrogateSerializer<NbtList, NbtListSurrogate>("NbtList", NbtList::class)
class KNbtCompoundSerializer : NbtSurrogateSerializer<NbtCompound, NbtCompoundSurrogate>("NbtCompound", NbtCompound::class)
class KNbtEndSerializer : NbtSurrogateSerializer<NbtEnd, NbtEndSurrogate>("NbtEnd", NbtEnd::class)

open class NbtSurrogateSerializer<A : NbtElement, B : NbtElementSurrogate>(name: String, private val klass: KClass<A>) :
    KSerializer<A> {

    private val delegate: KSerializer<NbtElement> = KNbtElementSerializer()
    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = SerialDescriptor(name + "Surrogate", delegate.descriptor)

    override fun serialize(encoder: Encoder, value: A) {
        encoder.encodeSerializableValue(delegate, value)
    }

    override fun deserialize(decoder: Decoder): A {
        val result = decoder.decodeSerializableValue(delegate)
        return klass.cast(result)
    }
}

class NbtAsStringSerializer : KSerializer<NbtCompound> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("NbtAsString", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): NbtCompound {
        val content = decoder.decodeString()
        return StringNbtReader.parse(content)
    }

    override fun serialize(encoder: Encoder, value: NbtCompound) {
        val result = StringNbtWriter().apply(value)
        encoder.encodeString(result)
    }
}
