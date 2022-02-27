package net.sakuragame.eternal.kirrajoiner

import net.sakuragame.kirracore.bukkit.KirraCoreBukkitAPI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.Plugin
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common5.Baffle
import taboolib.platform.BukkitPlugin
import taboolib.platform.util.sendLang
import java.util.concurrent.TimeUnit

@Suppress("SpellCheckingInspection")
object KirraJoiner : Plugin() {

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    private val baffle by lazy {
        Baffle.of(10, TimeUnit.SECONDS)
    }

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        e.player.reset(closeInventory = true, clearInventory = true)
    }

    @SubscribeEvent
    fun e(e: PlayerMoveEvent) {
        val player = e.player
        if (e.to.clone().subtract(0.0, 1.0, 0.0).block.type != Material.OBSIDIAN) {
            return
        }
        if (!baffle.hasNext(player.name)) {
            return
        }
        baffle.next(player.name)
        doAnimation(player)
        doJoin(player)
    }

    @SubscribeEvent
    fun e(e: EntityDamageEvent) {
        e.isCancelled = true
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        val player = e.player
        baffle.reset(player.name)
    }

    @SubscribeEvent
    fun e(e: AsyncPlayerChatEvent) {
        e.isCancelled = true
        e.player.sendMessage("&4&l➱ &c当前服务器禁止聊天.".colored())
    }

    private fun doAnimation(player: Player) {
        KirraCoreBukkitAPI.showLoadingTitle(player, "&6&l➱ &e正在唤醒角色 (${player.name}) &7@", true)
    }

    private fun doJoin(player: Player) {
        if (player.hasPermission("noobie_tutorial") || player.getNoobiePoints() != null) {
            KirraCoreBukkitAPI.teleportToSpawnServer(player)
            return
        }
        val isSucc = StoryDungeonCompat.join(player)
        if (!isSucc) {
            baffle.reset(player.name)
            player.sendLang("player-first-teleport-failed")
        }
    }
}