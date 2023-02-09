package me.boomber.zetalib.api.data_tracker

import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Tracked<T>(val dataTracker: DataTracker, val key: TrackedData<T>) : ReadWriteProperty<Any?, T> {
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
        dataTracker.get(key)

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        dataTracker.set(key, value)
    }
}
