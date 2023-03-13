package me.boomber.zetalib.api.state

/**
 * Annotates a property to be serialized to NBT
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class State(val name: String = "")
