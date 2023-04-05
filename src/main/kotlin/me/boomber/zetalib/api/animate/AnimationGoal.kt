package me.boomber.zetalib.api.animate

import net.minecraft.entity.ai.goal.Goal
import java.util.*

abstract class AnimationGoal(
    val entity: AnimatedEntity,
    val blocking: Boolean = false,
    val canBeInterrupt: Boolean = false
) : Goal() {
    init {
        if (blocking) {
            controls = EnumSet.of(Control.MOVE, Control.LOOK)
        }
    }

    abstract fun predicate(current: Animation): Boolean

    override fun canStart(): Boolean = predicate(entity.animation)
    override fun shouldRunEveryTick(): Boolean = true

    override fun shouldContinue(): Boolean {
        val success = predicate(entity.animation)
        val isPlaying = entity.animationTick < entity.animation.duration

        return success && isPlaying
    }

    override fun start() {
        entity.canBeInterrupt = canBeInterrupt
    }
}

open class PlayAnimationGoal(
    entity: AnimatedEntity,
    open val animation: Animation,
    blocking: Boolean = false,
    canBeInterrupt: Boolean = animation.isLooping
) : AnimationGoal(entity, blocking, canBeInterrupt) {
    override fun predicate(current: Animation): Boolean = current == animation

    override fun stop() {
        entity.playAnimation(Animation.NONE)
    }
}

open class SequenceAnimationGoal(
    entity: AnimatedEntity,
    fromAnimation: Animation,
    open val toAnimation: Animation,
    blocking: Boolean = false,
    canBeInterrupt: Boolean = true
) : PlayAnimationGoal(entity, fromAnimation, blocking, canBeInterrupt) {
    override fun stop() {
        entity.playAnimation(toAnimation)
    }
}
