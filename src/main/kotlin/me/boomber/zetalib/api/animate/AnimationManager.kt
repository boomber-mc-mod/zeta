package me.boomber.zetalib.api.animate

class AnimationManager(private val animations: List<Animation>) {
    operator fun get(animation: Animation): AnimationHandle =
        animations.indexOf(animation).let(::AnimationHandle)
    operator fun get(handle: AnimationHandle): Animation =
        animations.getOrElse(handle.index) { Animation.NONE }

    operator fun contains(handle: AnimationHandle): Boolean =
        handle.index in animations.indices
    operator fun contains(animation: Animation): Boolean =
        animation in animations

    fun fromNbt(animation: String): Animation {
        val result = animations.find { it.name == animation }
        return result ?: Animation.NONE
    }

    fun toNbt(animation: Animation): String =
        animation.name
}

