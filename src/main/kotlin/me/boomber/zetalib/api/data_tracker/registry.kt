package me.boomber.zetalib.api.data_tracker

import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityPose
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandler
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.decoration.painting.PaintingVariant
import net.minecraft.entity.passive.CatVariant
import net.minecraft.entity.passive.FrogVariant
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.particle.ParticleEffect
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.EulerAngle
import net.minecraft.util.math.GlobalPos
import net.minecraft.village.VillagerData
import java.util.*

fun <T : Any> trackedData(entity: Class<out Entity>, handler: TrackedDataHandler<T>): TrackedData<T> =
    DataTracker.registerData(entity, handler)

// Byte
inline fun <reified E : Entity> trackedByte(): TrackedData<Byte> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.BYTE)
// Integer
inline fun <reified E : Entity> trackedInt(): TrackedData<Int> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.INTEGER)
// Float
inline fun <reified E : Entity> trackedFloat(): TrackedData<Float> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.FLOAT)
// String
inline fun <reified E : Entity> trackedString(): TrackedData<String> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.STRING)
// Text Component
inline fun <reified E : Entity> trackedTextComponent(): TrackedData<Text> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.TEXT_COMPONENT)
// Optional Text Component
inline fun <reified E : Entity> trackedOptionalTextComponent(): TrackedData<Optional<Text>> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.OPTIONAL_TEXT_COMPONENT)
// ItemStack
inline fun <reified E : Entity> trackedItemStack(): TrackedData<ItemStack> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.ITEM_STACK)
// Optional Block State
inline fun <reified E : Entity> trackedOptionalBlockState(): TrackedData<Optional<BlockState>> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_STATE)
// Boolean
inline fun <reified E : Entity> trackedBoolean(): TrackedData<Boolean> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.BOOLEAN)
// Particle
inline fun <reified E : Entity> trackedParticle(): TrackedData<ParticleEffect> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.PARTICLE)
// Rotation
inline fun <reified E : Entity> trackedRotation(): TrackedData<EulerAngle> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.ROTATION)
// Block Pos
inline fun <reified E : Entity> trackedBlockPos(): TrackedData<BlockPos> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.BLOCK_POS)
// Optional Block Pos
inline fun <reified E : Entity> trackedOptionalBlockPos(): TrackedData<Optional<BlockPos>> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS)
// Facing
inline fun <reified E : Entity> trackedFacing(): TrackedData<Direction> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.FACING)
// Optional UUID
inline fun <reified E : Entity> trackedOptionalUuid(): TrackedData<Optional<UUID>> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.OPTIONAL_UUID)
// Optional Global Pos
inline fun <reified E : Entity> trackedOptionalGlobalPos(): TrackedData<Optional<GlobalPos>> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.OPTIONAL_GLOBAL_POS)
// NbtCompound
inline fun <reified E : Entity> trackedNbtCompound(): TrackedData<NbtCompound> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.NBT_COMPOUND)
// Villager Data
inline fun <reified E : Entity> trackedVillagerData(): TrackedData<VillagerData> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.VILLAGER_DATA)
// Optional Int
inline fun <reified E : Entity> trackedOptionalInt(): TrackedData<OptionalInt> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.OPTIONAL_INT)
// Entity Pose
inline fun <reified E : Entity> trackedEntityPose(): TrackedData<EntityPose> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.ENTITY_POSE)
// Cat Variant
inline fun <reified E : Entity> trackedCatVariant(): TrackedData<CatVariant> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.CAT_VARIANT)
// Frog Variant
inline fun <reified E : Entity> trackedFrogVariant(): TrackedData<FrogVariant> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.FROG_VARIANT)
// Painting Variant
inline fun <reified E : Entity> trackedPaintingVariant(): TrackedData<RegistryEntry<PaintingVariant>> =
    trackedData(E::class.java, TrackedDataHandlerRegistry.PAINTING_VARIANT)
