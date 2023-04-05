package me.boomber.zetalib.api.animate

import kotlinx.serialization.ExperimentalSerializationApi
import net.minecraft.world.World
import net.silkmc.silk.network.packet.s2cPacket
import software.bernie.geckolib.core.animation.Animation.LoopType

@OptIn(ExperimentalSerializationApi::class)
val animationPacket = s2cPacket<AnimationPacket>(AnimationPacket.id)

val LoopType.isLooping: Boolean
    get() = this == LoopType.LOOP

fun animationManagerOf(vararg animations: Animation) =
    AnimationManager(animations.toList())

fun AnimatedEntity.playAnimation(animation: Animation) {
    if (world.isClient) {
        return
    }

    if (this.animation != animation || canBeInterrupt) {
        val handle = animationManager[animation]
        val packet = AnimationPacket(id, handle)
        animationPacket.sendToAll(packet)
    }
}

/**
 * Spawns the entity and plays an animation on it.
 */
fun AnimatedEntity.spawn(world: World, animation: Animation = Animation.NONE) {
    world.spawnEntity(this)
    playAnimation(animation)
}
