package me.boomber.zetalib.api.animate

import net.minecraft.entity.EntityType
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.world.World
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.AnimatableManager

abstract class AnimatedEntity(entityType: EntityType<out AnimatedEntity>, world: World) :
    PathAwareEntity(entityType, world), GeoEntity {

    abstract val animationManager: AnimationManager
    abstract val animatable: AnimatableInstanceCache

    var animationTick: Int = 0
        private set
    var animation: Animation = Animation.NONE
        private set
    var canBeInterrupt: Boolean = true
        internal set

    fun setAnimation(animationHandle: AnimationHandle) {
        val newAnimation = animationManager[animationHandle]

        if (newAnimation != animation) {
            animationTick = 0
            animation = newAnimation
        }
    }

    fun tickAnimation() {
        animationTick++

        if (animation.isLooping && animationTick >= animation.duration) {
            animationTick = 0
            return
        }

        if (animationTick >= animation.duration) {
            animation = Animation.NONE
        }
    }

    override fun tick() {
        super.tick()

        if (!animation.isNone) {
            tickAnimation()
        }
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return animatable
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        animationTick = nbt.getInt("animationTick")
        pitch = nbt.getFloat("pitch")
        yaw = nbt.getFloat("yaw")
        headYaw = yaw
        bodyYaw = yaw
        animation = animationManager.fromNbt(nbt.getString("animation"))
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        nbt.putInt("animationTick", animationTick)
        nbt.putFloat("pitch", pitch)
        nbt.putFloat("yaw", yaw)
        nbt.putString("animation", animationManager.toNbt(animation))
    }
}