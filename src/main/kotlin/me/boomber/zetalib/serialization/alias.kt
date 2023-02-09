package me.boomber.zetalib.serialization

import kotlinx.serialization.Serializable
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtByte
import net.minecraft.nbt.NbtByteArray
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtDouble
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtEnd
import net.minecraft.nbt.NbtFloat
import net.minecraft.nbt.NbtInt
import net.minecraft.nbt.NbtIntArray
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtLong
import net.minecraft.nbt.NbtLongArray
import net.minecraft.nbt.NbtShort
import net.minecraft.nbt.NbtString
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i
import net.silkmc.silk.core.serialization.serializers.ResourceLocationSerializer

typealias KIdentifier = @Serializable(with = ResourceLocationSerializer::class) Identifier
typealias KVec3d = @Serializable(with = Vec3dSerializer::class) Vec3d
typealias KBlockPos = @Serializable(with = BlockPosSerializer::class) BlockPos
typealias KVec3i = @Serializable(with = Vec3iSerializer::class) Vec3i

typealias KNbtElement = @Serializable(with = KNbtElementSerializer::class) NbtElement
typealias KNbtCompound = @Serializable(with = KNbtCompoundSerializer::class) NbtCompound
typealias KNbtList = @Serializable(with = KNbtListSerializer::class) NbtList
typealias KNbtByteArray = @Serializable(with = KNbtByteArraySerializer::class) NbtByteArray
typealias KNbtIntArray = @Serializable(with = KNbtIntArraySerializer::class) NbtIntArray
typealias KNbtLongArray = @Serializable(with = KNbtLongArraySerializer::class) NbtLongArray
typealias KNbtString = @Serializable(with = KNbtStringSerializer::class) NbtString
typealias KNbtByte = @Serializable(with = KNbtByteSerializer::class) NbtByte
typealias KNbtShort = @Serializable(with = KNbtShortSerializer::class) NbtShort
typealias KNbtInt = @Serializable(with = KNbtIntSerializer::class) NbtInt
typealias KNbtLong = @Serializable(with = KNbtLongSerializer::class) NbtLong
typealias KNbtFloat = @Serializable(with = KNbtFloatSerializer::class) NbtFloat
typealias KNbtDouble = @Serializable(with = KNbtDoubleSerializer::class) NbtDouble
typealias KNbtEnd = @Serializable(with = KNbtEndSerializer::class) NbtEnd
typealias NbtCompoundString = @Serializable(with = NbtAsStringSerializer::class) NbtCompound

typealias KItemStack = @Serializable(with = ItemStackSerializer::class) ItemStack
typealias ItemStackAsString = @Serializable(with = ItemStackAsStringSerializer::class) ItemStack
