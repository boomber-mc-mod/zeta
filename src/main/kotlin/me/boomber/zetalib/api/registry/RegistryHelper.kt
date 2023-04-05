package me.boomber.zetalib.api.registry

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

fun <T : Entity> register(id: Identifier, entityType: EntityType<T>): EntityType<out T> =
    Registry.register(Registries.ENTITY_TYPE, id, entityType)

fun <T : Block> register(id: Identifier, block: T): T =
    Registry.register(Registries.BLOCK, id, block)

fun <T : Item> register(id: Identifier, item: T): T =
    Registry.register(Registries.ITEM, id, item)

fun <T : BlockEntity> register(id: Identifier, blockEntity: BlockEntityType<T>): BlockEntityType<out T> =
    Registry.register(Registries.BLOCK_ENTITY_TYPE, id, blockEntity)

fun register(id: Identifier, status: StatusEffect): StatusEffect =
    Registry.register(Registries.STATUS_EFFECT, id, status)

fun register(id: Identifier, enchantment: Enchantment): Enchantment =
    Registry.register(Registries.ENCHANTMENT, id, enchantment)

fun <T : LivingEntity> register(type: EntityType<out T>, attribute: DefaultAttributeContainer.Builder) =
    FabricDefaultAttributeRegistry.register(type, attribute)

fun <T : Entity> register(type: EntityType<out T>, renderer: EntityRendererFactory<T>) =
    EntityRendererRegistry.register(type, renderer)
fun <T : BlockEntity> register(type: BlockEntityType<out T>, renderer: BlockEntityRendererFactory<T>) =
    BlockEntityRendererFactories.register(type, renderer)

fun <T : Entity> entityType(
    factory: (EntityType<out T>, World) -> T,
    spawnGroup: SpawnGroup,
    builder: FabricEntityTypeBuilder<T>.() -> Unit
): EntityType<T> =
    FabricEntityTypeBuilder.create(spawnGroup, factory).apply(builder).build()
fun <T : BlockEntity> blockEntityType(
    factory: (BlockPos, BlockState) -> T,
    vararg blocks: Block,
    builder: FabricBlockEntityTypeBuilder<T>.() -> Unit
): BlockEntityType<T> =
    FabricBlockEntityTypeBuilder.create(factory, *blocks).apply(builder).build()

fun FabricEntityTypeBuilder<out Entity>.dimensions(width: Float, height: Float, fixed: Boolean = true): FabricEntityTypeBuilder<out Entity> =
    dimensions(EntityDimensions(width, height, fixed))
