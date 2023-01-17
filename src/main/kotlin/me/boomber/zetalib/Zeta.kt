package me.boomber.zetalib

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Zeta : ModInitializer, ClientModInitializer {
    const val MOD_ID: String = "zeta"
    val logger: Logger = LoggerFactory.getLogger(MOD_ID)

    infix fun id(path: String): Identifier = Identifier(MOD_ID, path)

    override fun onInitialize() {
        logger.info("Initializing zeta-lib")
    }

    override fun onInitializeClient() {
        logger.info("Initializing zeta-lib client")
    }
}