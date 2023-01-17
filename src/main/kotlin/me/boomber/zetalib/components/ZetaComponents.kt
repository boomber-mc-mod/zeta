package me.boomber.zetalib.components

import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer

object ZetaComponents : WorldComponentInitializer {
    override fun registerWorldComponentFactories(registry: WorldComponentFactoryRegistry) = with(registry) {
    }
}
