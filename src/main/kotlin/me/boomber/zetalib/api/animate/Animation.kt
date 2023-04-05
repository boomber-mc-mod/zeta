package me.boomber.zetalib.api.animate

import me.boomber.zetalib.api.math.inTicks
import software.bernie.geckolib.core.animation.Animation.LoopType
import software.bernie.geckolib.core.animation.RawAnimation
import kotlin.time.Duration

class Animation(val name: String, val duration: Int = 0, val loop: LoopType = LoopType.PLAY_ONCE) {
    constructor(name: String, duration: Duration, loop: LoopType = LoopType.PLAY_ONCE) : this(name, duration.inTicks, loop)

    fun build(): RawAnimation =
        RawAnimation.begin().then(name, loop)

    val isLooping: Boolean get() = loop.isLooping
    val isNone get() = this == NONE

    companion object {
        val NONE = Animation("none")
    }
}