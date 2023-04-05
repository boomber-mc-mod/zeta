package me.boomber.zetalib.api.animate

import software.bernie.geckolib.core.animation.AnimationController
import software.bernie.geckolib.core.animation.AnimationState
import software.bernie.geckolib.core.`object`.PlayState

class AnimatedHandler : AnimationController.AnimationStateHandler<AnimatedEntity> {
    override fun handle(state: AnimationState<AnimatedEntity>): PlayState {
        val animation = state.animatable.animation

        if (animation.isNone) {
            state.controller.forceAnimationReset()
            return PlayState.STOP
        }

        return state.setAndContinue(animation.build())
    }
}