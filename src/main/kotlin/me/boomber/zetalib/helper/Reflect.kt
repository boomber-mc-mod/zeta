package me.boomber.zetalib.helper

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

inline fun <T : KAnnotatedElement, reified A : Annotation, R> T.annotated(f: T.(A) -> R): R? =
    this.findAnnotation<A>()?.let { this.f(it) }

inline fun <reified A> KClass<*>.annotatedProperties(): Map<KProperty1<*, *>, A>
        where A : Annotation =
    memberProperties.associateWithNotNull { it.findAnnotation<A>() }

inline fun <reified K, reified V> Iterable<K>.associateWithNotNull(crossinline f: (K) -> V?): Map<K, V> =
    this.associateWith(f)
        .filterValues { it != null }
        .mapValues { it.value!! }
