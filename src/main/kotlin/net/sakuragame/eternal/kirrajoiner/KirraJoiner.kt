package net.sakuragame.eternal.kirrajoiner

import net.sakuragame.kirracore.bukkit.KirraCoreBukkitAPI
import org.bukkit.Material
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.Plugin
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common5.Baffle
import java.util.concurrent.TimeUnit

object KirraJoiner : Plugin() {

    val baffle by lazy {
        Baffle.of(10, TimeUnit.SECONDS)
    }

    @SubscribeEvent
    fun e(e: PlayerMoveEvent) {
        val player = e.player
        player.reset(closeInventory = true, clearInventory = true)
        if (e.to.clone().subtract(0.0, 1.0, 0.0).block.type != Material.OBSIDIAN || !baffle.hasNext(player.name)) {
            return
        }
        KirraCoreBukkitAPI.teleportToSpawnServer(player)
        player.sendTitle("", "&6&l正在传送.".colored(), 10, 1000, 10)
        baffle.next(player.name)
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        baffle.reset(e.player.name)
    }
}