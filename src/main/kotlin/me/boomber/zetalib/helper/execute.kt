package me.boomber.zetalib.helper

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World

val minecraftClient: MinecraftClient
    get() = MinecraftClient.getInstance()

inline fun World.serverSide(block: (ServerWorld) -> Unit) {
    if (this is ServerWorld) {
        block(this)
    }
}

inline fun World.clientSide(block: (ClientWorld) -> Unit) {
    if (this is ClientWorld) {
        block(this)
    }
}

inline fun PlayerEntity.serverSide(block: (ServerPlayerEntity) -> Unit) {
    if (this is ServerPlayerEntity) {
        block(this)
    }
}

inline fun PlayerEntity.clientSide(block: (ClientPlayerEntity) -> Unit) {
    if (this is ClientPlayerEntity) {
        block(this)
    }
}
