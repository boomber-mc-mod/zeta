package me.boomber.zetalib

import me.boomber.zetalib.client.area.AreaDetectorRenderer
import me.boomber.zetalib.commands.Commands
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.minecraft.util.Identifier

object Zeta : ModInitializer, ClientModInitializer {
    const val MOD_ID = "zeta"

    infix fun id(path: String): Identifier = Identifier(MOD_ID, path)

    override fun onInitialize() {
        TODO("Not yet implemented")
    }

    override fun onInitializeClient() {
        TODO("Not yet implemented")
    }
}