package me.boomber.zetalib

import me.boomber.zetalib.api.animate.animationPacket

object ZetaLib {
    private var initialized = false

    fun init() {
        if (!initialized) {
            animationPacket.receiveOnClient { packet, context ->
                packet.receive(context.client)
            }
        }

        initialized = true
    }
}
