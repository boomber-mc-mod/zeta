package me.boomber.zetalib.api.command

import me.boomber.zetalib.api.math.clamp
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.text.Text
import net.silkmc.silk.core.text.literalText

class ItemBoxScreen(items: List<ItemStack>) : NamedScreenHandlerFactory {
    private val rows = clamp(items.size / 9 + 1, 1, 6)
    private val inventory = SimpleInventory(rows * 9).apply {
        items.take(rows * 9).forEachIndexed { index, itemStack ->
            setStack(index, itemStack)
        }
    }

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

    override fun getDisplayName(): Text {
        return literalText("Item Box")
    }
}
