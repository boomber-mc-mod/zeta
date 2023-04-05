package me.boomber.zetalib.api.animate

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier
import net.minecraft.world.World

@Serializable
data class AnimationPacket(val id: Int, val handle: AnimationHandle) {
    companion object {
        val id = Identifier("zetalib:animation")
    }

    fun receive(client: MinecraftClient) {
        val world = client.world ?: return

        client.execute {
            val entity = world.getEntityById(id) as? AnimatedEntity ?: return@execute
            entity.setAnimation(handle)
        }
    }
}
