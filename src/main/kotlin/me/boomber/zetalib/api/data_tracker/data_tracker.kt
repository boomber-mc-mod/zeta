package me.boomber.zetalib.api.data_tracker

import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData

/**
 * Create a [TrackedProperty] delegate that automatically sync the value with [DataTracker]
 */
fun <T> DataTracker.tracked(key: TrackedData<T>): TrackedProperty<T> =
    TrackedProperty(this, key)

