package net.sakuragame.eternal.kirrajoiner.function

import net.sakuragame.eternal.kirrajoiner.colored
import net.sakuragame.eternal.kirrajoiner.function.speedclicker.FunctionSpeedClickerGUI
import net.sakuragame.eternal.kirrajoiner.reset
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import taboolib.common.platform.event.SubscribeEvent

object FunctionCommonListener {

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        val player = e.player
        player.reset(closeInventory = true, clearInventory = true)
    }

    @SubscribeEvent
    fun e(e: InventoryClickEvent) {
        if (canBypass(e.whoClicked as Player)) {
            return
        }
        e.isCancelled = true
    }


    @SubscribeEvent
    fun e(e: PlayerSwapHandItemsEvent) {
        e.isCancelled = true
    }

    @SubscribeEvent
    fun e(e: BlockPlaceEvent) {
        if (canBypass(e.player)) {
            return
        }
        e.isCancelled = true
    }

    @SubscribeEvent
    fun e(e: PlayerDropItemEvent) {
        if (canBypass(e.player)) {
            return
        }
        e.isCancelled = true
    }

    @SubscribeEvent
    fun e(e: EntityDamageByEntityEvent) {
        if (canBypass(e.damager as? Player ?: return)) {
            return
        }
        e.isCancelled = true
    }

    @SubscribeEvent
    fun e(e: EntityDamageEvent) {

        if (e.entity is Player) {
            e.isCancelled = true
        }
    }

    @SubscribeEvent
    fun e(e: AsyncPlayerChatEvent) {
        e.isCancelled = true
        e.player.sendMessage("&4&l➱ &c当前服务器禁止聊天.".colored())
    }

    private fun canBypass(player: Player) = player.gameMode == GameMode.CREATIVE
}