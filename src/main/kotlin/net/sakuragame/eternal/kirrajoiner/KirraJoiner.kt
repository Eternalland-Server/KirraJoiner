package net.sakuragame.eternal.kirrajoiner

import net.sakuragame.eternal.justmessage.api.common.NotifyBox
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkitAPI
import net.sakuragame.eternal.kirrajoiner.function.FunctionNotifyBox
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.Plugin
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.common5.Baffle
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.platform.BukkitPlugin
import java.util.concurrent.TimeUnit

@Suppress("SpellCheckingInspection")
object KirraJoiner : Plugin() {

    @Config
    lateinit var conf: Configuration
        private set

    val teleportTo: String
        get() = conf.getString("settings.teleport-to")!!

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    val baffle by lazy {
        Baffle.of(2, TimeUnit.SECONDS)
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
        doJoin(player)
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        val player = e.player
        baffle.reset(player.name)
    }

    fun doAnimation(player: Player) {
        player.playSound(player.location, Sound.BLOCK_END_PORTAL_FRAME_FILL, 1f, 1.5f)
        KirraCoreBukkitAPI.showLoadingAnimation(player, "&6&l➱ &e正在唤醒角色 (${player.name}) &7@", true)
    }

    private fun doJoin(player: Player) {
        submit(async = true, delay = 3L) {
            if (player.hasPermission("noobie_tutorial")) {
                KirraCoreBukkitAPI.teleportPlayerToServerByBalancing(teleportTo, player.uniqueId)
                doAnimation(player)
                return@submit
            }
            player.velocity = player.location.direction.multiply(-2)
            player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1f, 1.5f)
            submit(delay = 3L) {
                NotifyBox(FunctionNotifyBox.KEY, "&6&l提示", listOf("我们强烈建议您使用全屏游戏", "以获得最好的游戏体验."))
                    .setConfirmText("我知道了")
                    .setCancelText("我偏不")
                    .open(player, false)
            }
        }
    }
}