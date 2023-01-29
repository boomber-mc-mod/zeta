package me.boomber.zetalib.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i
import net.minecraft.util.registry.Registry

@Serializable
@SerialName("Vec3d")
data class Vec3dSurrogate(val x: Double, val y: Double, val z: Double)

class Vec3dSerializer : SurrogateSerializer<Vec3d, Vec3dSurrogate>() {
    override val delegate: KSerializer<Vec3dSurrogate> =
        Vec3dSurrogate.serializer()

    override fun encode(value: Vec3d): Vec3dSurrogate {
        return Vec3dSurrogate(value.x, value.y, value.z)
    }

    override fun decode(value: Vec3dSurrogate): Vec3d {
        return Vec3d(value.x, value.y, value.z)
    }
}

@Serializable
@SerialName("BlockPos")
data class BlockPosSurrogate(val x: Int, val y: Int, val z: Int)

class BlockPosSerializer : SurrogateSerializer<BlockPos, BlockPosSurrogate>() {
    override val delegate: KSerializer<BlockPosSurrogate> = BlockPosSurrogate.serializer()

    override fun encode(value: BlockPos): BlockPosSurrogate {
        return BlockPosSurrogate(value.x, value.y, value.z)
    }

    override fun decode(value: BlockPosSurrogate): BlockPos {
        return BlockPos(value.x, value.y, value.z)
    }
}

@Serializable
@SerialName("Vec3i")
data class Vec3iSurrogate(val x: Int, val y: Int, val z: Int)

class Vec3iSerializer : SurrogateSerializer<Vec3i, Vec3iSurrogate>() {
    override val delegate: KSerializer<Vec3iSurrogate> = Vec3iSurrogate.serializer()

    override fun encode(value: Vec3i): Vec3iSurrogate {
        return Vec3iSurrogate(value.x, value.y, value.z)
    }

    override fun decode(value: Vec3iSurrogate): Vec3i {
        return Vec3i(value.x, value.y, value.z)
    }
}

@Serializable
@SerialName("ItemStack")
data class ItemStackSurrogate(val id: KIdentifier, val count: Int, val tag: KNbtCompound?)

class ItemStackSerializer : SurrogateSerializer<ItemStack, ItemStackSurrogate>() {
    override val delegate: KSerializer<ItemStackSurrogate> = ItemStackSurrogate.serializer()

    override fun decode(value: ItemStackSurrogate): ItemStack {
        return ItemStack(Registry.ITEM.get(value.id), value.count).apply {
            nbt = value.tag
        }
    }

    override fun encode(value: ItemStack): ItemStackSurrogate {
        return ItemStackSurrogate(Registry.ITEM.getId(value.item), value.count, value.nbt)
    }
}
