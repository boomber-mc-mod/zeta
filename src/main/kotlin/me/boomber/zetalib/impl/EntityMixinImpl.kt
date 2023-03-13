package me.boomber.zetalib.impl

import me.boomber.zetalib.api.state.State
import me.boomber.zetalib.api.Zeta
import me.boomber.zetalib.api.state.loadStateFromNbt
import me.boomber.zetalib.api.state.saveStateToNbt
import me.boomber.zetalib.helper.annotated
import me.boomber.zetalib.helper.annotatedProperties
import net.minecraft.entity.Entity
import net.minecraft.nbt.NbtCompound

class EntityMixinImpl(private val self: Entity) {
    private val klass = self.javaClass.kotlin

    fun onLoadNbtData(tag: NbtCompound) {
        klass.annotated { _: Zeta ->
            klass.annotatedProperties<State>().forEach { (prop, state) ->
                loadStateFromNbt(self, prop, state, tag)
            }
        }
    }

    fun onSaveNbtData(tag: NbtCompound) {
        klass.annotated { _: Zeta ->
            klass.annotatedProperties<State>().forEach { (prop, state) ->
                saveStateToNbt(self, prop, state, tag)
            }
        }
    }
}
