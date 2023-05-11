package me.boomber.zetalib.api.math

import net.minecraft.util.math.Vec2f
import kotlin.time.Duration

operator fun Vec2f.component1() = x
operator fun Vec2f.component2() = y

fun <T : Comparable<T>> clamp(value: T, min: T, max: T) =
    if (value < min) min else if (value > max) max else value

val Duration.inTicks get() = (inWholeMilliseconds / 50).toInt()
