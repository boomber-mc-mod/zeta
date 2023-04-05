package me.boomber.zetalib.api.animate

import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.world.World

abstract class LivingAnimatedEntity(entityType: EntityType<out AnimatedEntity>, world: World) :
    AnimatedEntity(entityType, world) {
    open val spawnAnimation: Animation = Animation.NONE
    open val deathAnimation: Animation = Animation.NONE

    override fun onKilledBy(adversary: LivingEntity?) {
        super.onKilledBy(adversary)
        playAnimation(deathAnimation)
    }
}