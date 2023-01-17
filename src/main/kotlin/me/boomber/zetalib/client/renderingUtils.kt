package me.boomber.zetalib.client

import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3f
import net.minecraft.util.math.Vector4f
import net.silkmc.silk.core.text.LiteralTextBuilder
import net.silkmc.silk.core.text.literalText
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Build a list of vertices to draw a sphere at the origin.
 */
fun makeSphere(radius: Float, tessellation: Int = 32): List<Vec3f> {
    val pi = PI.toFloat()

    val phiStep = pi * 2 / tessellation
    val thetaStep = pi / tessellation

    fun computeVertex(theta: Float, phi: Float): Vec3f {
        val x = radius * sin(theta) * cos(phi)
        val y = radius * sin(theta) * sin(phi)
        val z = radius * cos(theta)
        return Vec3f(x, y, z)
    }

    fun tessellate(step: Float) = makeList(tessellation, step)

    return buildList {
        tessellate(phiStep).sumForEach { phi1, phi2 ->
            tessellate(thetaStep).sumForEach { theta1, theta2 ->
                val a = computeVertex(theta1, phi1)
                val b = computeVertex(theta2, phi1)
                val c = computeVertex(theta1, phi2)
                val d = computeVertex(theta2, phi2)

                // first triangle
                add(a); add(b); add(c)
                // second triangle
                add(b); add(d); add(c)
            }
        }

    }
}

/**
 * Build a list of vertices to draw a cube at the origin.
 */
fun makeCube(width: Float, height: Float, length: Float): List<Vec3f> {
    val w = width / 2
    val h = height / 2
    val l = length / 2

    return buildList {
        // front
        add(Vec3f(-w, -h, l))
        add(Vec3f(w, -h, l))
        add(Vec3f(w, h, l))
        add(Vec3f(-w, h, l))
        // back
        add(Vec3f(-w, -h, -l))
        add(Vec3f(w, -h, -l))
        add(Vec3f(w, h, -l))
        add(Vec3f(-w, h, -l))
        // left
        add(Vec3f(-w, -h, l))
        add(Vec3f(-w, -h, -l))
        add(Vec3f(-w, h, -l))
        add(Vec3f(-w, h, l))
        // right
        add(Vec3f(w, -h, l))
        add(Vec3f(w, -h, -l))
        add(Vec3f(w, h, -l))
        add(Vec3f(w, h, l))
        // top
        add(Vec3f(-w, h, l))
        add(Vec3f(w, h, l))
        add(Vec3f(w, h, -l))
        add(Vec3f(-w, h, -l))
        // bottom
        add(Vec3f(-w, -h, l))
        add(Vec3f(w, -h, l))
        add(Vec3f(w, -h, -l))
        add(Vec3f(-w, -h, -l))
    }
}

inline fun <reified T> makeList(size: Int, value: T) =
    Array(size) { value }.toList()

/**
 * Computes a series of sums and calls the given function with each pair of consecutive sums.
 */
fun List<Float>.sumForEach(f: (Float, Float) -> Unit) {
    fold(0f) { acc, cur ->
        f(acc, acc + cur)
        acc + cur
    }
}

fun TextRenderer.draw(
    stack: MatrixStack,
    content: String,
    width: Int,
    x: Float = 0f,
    y: Float = 0f,
    alignment: Alignment = Alignment.CENTER,
    shadow: Boolean = true,
    color: Int = 0xFFFFFF,
    builder: LiteralTextBuilder.() -> Unit = {}
) = draw(stack, literalText(content, builder), width, x, y, alignment, shadow, color)

fun TextRenderer.draw(
    stack: MatrixStack,
    text: Text,
    width: Int = getWidth(text),
    x: Float = 0f,
    y: Float = 0f,
    alignment: Alignment = Alignment.CENTER,
    shadow: Boolean = true,
    color: Int = 0xFFFFFF,
    debugBackground: Boolean = false,
) {
    val offset = (width - getWidth(text)) * alignment.offset

    if (debugBackground)
        DrawableHelper.fill(stack, x.toInt(), y.toInt(), x.toInt() + width, y.toInt() + fontHeight, 0x80000000.toInt())

    if (shadow)
        drawWithShadow(stack, text, x + offset, y, color)
    else
        draw(stack, text, x + offset, y, color)
}

enum class Alignment(val offset: Float) {
    LEFT(0.0f),
    CENTER(0.5f),
    RIGHT(1.0f);
}

fun ItemRenderer.draw(matrices: MatrixStack, itemStack: ItemStack) {
    fun getTranslation(stack: MatrixStack) = Vector4f(0f, 0f, 0f, 1f)
        .also { it.transform(stack.peek().positionMatrix) }
        .let(::Vec3f)

    val result = getTranslation(matrices)
    renderGuiItemIcon(itemStack, result.x.toInt(), result.y.toInt())
}
