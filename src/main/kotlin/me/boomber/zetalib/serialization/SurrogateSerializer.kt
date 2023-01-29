package me.boomber.zetalib.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@OptIn(ExperimentalSerializationApi::class)
abstract class SurrogateSerializer<T, S> : KSerializer<T> {
    abstract val delegate: KSerializer<S>

    override val descriptor: SerialDescriptor by lazy {
        SerialDescriptor(delegate.descriptor.serialName, delegate.descriptor)
    }

    abstract fun decode(value: S): T
    abstract fun encode(value: T): S

    override fun deserialize(decoder: Decoder): T {
        return decoder.decodeSerializableValue(delegate).let(::decode)
    }

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeSerializableValue(delegate, encode(value))
    }
}