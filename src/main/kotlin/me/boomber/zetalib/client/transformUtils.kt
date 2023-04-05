package me.boomber.zetalib.client

import net.minecraft.client.model.ModelPartBuilder
import net.minecraft.client.model.ModelPartData
import net.minecraft.client.model.ModelTransform
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Vec3d
import org.joml.Quaternionf as Quaternion
import org.joml.Matrix3f
import org.joml.Matrix4f


inline fun <T> MatrixStack.scope(crossinline block: MatrixStack.() -> T): T =
    try {
        push()
        block()
    } finally {
        pop()
    }

fun MatrixStack.translate(vec: Vec3d) = translate(vec.x, vec.y, vec.z)
fun MatrixStack.rotate(pitch: Double = 0.0, yaw: Double = 0.0, roll: Double = 0.0) =
    multiply(Quaternion().rotateXYZ(pitch.toFloat(), yaw.toFloat(), roll.toFloat()))

fun MatrixStack.scale(scale: Double) = scale(scale.toFloat(), scale.toFloat(), scale.toFloat())

operator fun MatrixStack.Entry.component1(): Matrix4f = positionMatrix
operator fun MatrixStack.Entry.component2(): Matrix3f = normalMatrix

inline fun VertexConsumer.point(crossinline builder: VertexConsumer.() -> Unit) {
    builder()
    next()
}

fun ModelPartData.addChild(name: String, transform: ModelTransform, builder: ModelPartBuilder.() -> Unit): ModelPartData =
    addChild(name, ModelPartBuilder.create().apply(builder), transform)
