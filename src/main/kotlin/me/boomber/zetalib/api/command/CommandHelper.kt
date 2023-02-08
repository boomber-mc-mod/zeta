package me.boomber.zetalib.api.command

import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.silkmc.silk.commands.CommandBuilder
import net.silkmc.silk.core.kotlin.ticks
import net.silkmc.silk.core.task.mcCoroutineTask
import kotlin.time.Duration

fun <Source : CommandSource> CommandBuilder<Source, *, *>.runsSuspend(
    sync: Boolean = true,
    client: Boolean = false,
    howOften: Long = 1,
    period: Duration = 1.ticks,
    delay: Duration = Duration.ZERO,
    block: suspend CommandContext<Source>.() -> Unit
) {
    runs {
        mcCoroutineTask(sync = sync, client = client, howOften = howOften, period = period, delay = delay) {
            block(this@runs)
        }
    }
}

fun ServerCommandSource.showItemBox(inventory: Inventory, name: Text = ItemBoxScreen.NAME) {
    val screen = ItemBoxScreen(inventory, name)
    this.player?.openHandledScreen(screen)
}

fun ServerCommandSource.showItemBox(items: List<ItemStack>, name: Text = ItemBoxScreen.NAME) {
    val inventory = ItemBoxScreen.inventory(items)
    showItemBox(inventory, name)
}

fun ServerCommandSource.showItemBox(vararg items: Item, name: Text = ItemBoxScreen.NAME) =
    showItemBox(items.map { it.defaultStack }, name)
