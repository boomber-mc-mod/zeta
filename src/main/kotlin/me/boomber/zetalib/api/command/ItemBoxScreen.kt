package me.boomber.zetalib.api.command

import me.boomber.zetalib.api.math.clamp
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtList
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.text.Text
import net.silkmc.silk.core.text.literalText

open class ItemBoxScreen(open val inventory: Inventory, open val name: Text = NAME) : NamedScreenHandlerFactory {
    private val rows get() = alignRows(inventory.size())

    private val type = when (rows) {
        1 -> ScreenHandlerType.GENERIC_9X1
        2 -> ScreenHandlerType.GENERIC_9X2
        3 -> ScreenHandlerType.GENERIC_9X3
        4 -> ScreenHandlerType.GENERIC_9X4
        5 -> ScreenHandlerType.GENERIC_9X5
        6 -> ScreenHandlerType.GENERIC_9X6
        else -> ScreenHandlerType.GENERIC_9X1
    }

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler {
        return GenericContainerScreenHandler(type, syncId, playerInventory, inventory, rows)
    }

    override fun getDisplayName(): Text = name

    companion object {
        val NAME = literalText("Item Box")

        fun alignRows(size: Int) =
            clamp(size / 9, 1, 6)

        fun inventory(items: List<ItemStack>) =
            SimpleInventory(alignRows(items.size) * 9).apply {
                items.forEachIndexed { index, itemStack -> setStack(index, itemStack) }
            }

        fun inventory(items: Map<Int, ItemStack>) =
            SimpleInventory(alignRows(items.keys.max())).apply {
                items.forEach { (index, itemStack) -> setStack(index, itemStack) }
            }

        fun inventory(nbt: NbtList, size: Int = 27) =
            SimpleInventory(size).apply {
                readNbtList(nbt)
            }
    }
}
