package me.boomber.zetalib.client

import kotlinx.serialization.Serializable
import net.minecraft.client.render.*
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Vec2f
import org.joml.Matrix3f
import org.joml.Vector4f
import org.joml.Vector3f as Vec3f

fun VertexConsumer.vertex(
    x: Float,
    y: Float,
    z: Float,
    u: Float,
    v: Float,
    normalX: Float = 0f,
    normalY: Float = 1f,
    normalZ: Float = 0f,
    overlay: Int = OverlayTexture.DEFAULT_UV,
    light: Int = LightmapTextureManager.MAX_LIGHT_COORDINATE,
    red: Float = 0f,
    green: Float = 0f,
    blue: Float = 0f,
    alpha: Float = 1f,
) =
    vertex(x, y, z, red, green, blue, alpha, u, v, overlay, light, normalX, normalY, normalZ)

@Serializable
data class Color(val value: Int) {
    companion object {
        private infix fun Int.asColor(bit: Int): Float = (this shr bit and 0xFF) / 256f
        private val Int.alpha get() = this asColor 24
        private val Int.red get() = this asColor 16
        private val Int.green get() = this asColor 8
        private val Int.blue get() = this asColor 0

        private infix fun Float.asColor(bit: Int): Int = (this * 256).toInt() shl bit
        private val Float.alpha get() = this asColor 24
        private val Float.red get() = this asColor 16
        private val Float.green get() = this asColor 8
        private val Float.blue get() = this asColor 0
    }

    constructor(red: Float, green: Float, blue: Float, alpha: Float = 1f) : this(
        red.red or green.green or blue.blue or alpha.alpha
    )

    val red get() = value.red
    val green get() = value.green
    val blue get() = value.blue
    val alpha get() = value.alpha

    fun withAlpha(alpha: Float) = Color(red, green, blue, alpha)

    fun toVec4f() = Vector4f(red, green, blue, alpha)

    fun toFloats() = FloatArray(4).apply {
        this[0] = value.red
        this[1] = value.green
        this[2] = value.blue
        this[3] = value.alpha
    }
}

fun VertexConsumerProvider.build(
    layer: RenderLayer,
    transform: MatrixStack.Entry,
    color: Color,
    overlay: Int = OverlayTexture.DEFAULT_UV,
    light: Int = LightmapTextureManager.MAX_LIGHT_COORDINATE,
    normal: Vec3f = Vec3f(0f, 1f, 0f),
    builder: VertexConsumerBuilder.() -> Unit
) = getBuffer(layer).build(transform, color, overlay, light, normal, builder)

fun VertexConsumer.build(
    transform: MatrixStack.Entry,
    color: Color,
    overlay: Int = OverlayTexture.DEFAULT_UV,
    light: Int = LightmapTextureManager.MAX_LIGHT_COORDINATE,
    normal: Vec3f = Vec3f(0f, 1f, 0f),
    builder: VertexConsumerBuilder.() -> Unit
) = VertexConsumerBuilder(this, transform, color, normal, overlay, light).builder()

class VertexConsumerBuilder(
    private val consumer: VertexConsumer,
    private val transform: MatrixStack.Entry,
    private var color: Color,
    private var normal: Vec3f,
    private var overlay: Int,
    private var light: Int
) {
    private val positionMat = transform.positionMatrix
    private val normalMat = transform.normalMatrix

    fun withColor(argb: Int) = apply {
        color = Color(argb)
    }

    fun raw(builder: VertexConsumer.() -> Unit) = apply {
        consumer.builder()
    }

    fun vertex(pos: Vec3f, uv: Vec2f, color: Color = this.color, normal: Vec3f = this.normal) = apply {
        consumer.vertex(positionMat, pos.x, pos.y, pos.z)
            .normal(normalMat, normal.x, normal.y, normal.z)
            .color(color.value)
            .overlay(overlay)
            .light(light)
            .texture(uv.x, uv.y)
            .next()
    }

    companion object {
        val IDENTITY_NORMAL = Matrix3f()
    }
}