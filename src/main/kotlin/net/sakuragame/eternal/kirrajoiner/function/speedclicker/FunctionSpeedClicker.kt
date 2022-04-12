package net.sakuragame.eternal.kirrajoiner.function.speedclicker

import net.sakuragame.eternal.kirrajoiner.Profile
import net.sakuragame.eternal.kirrajoiner.Profile.Companion.profile
import net.sakuragame.eternal.kirrajoiner.colored
import net.sakuragame.eternal.kirrajoiner.function.speedclicker.sub.SpeedClickerStatus
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem
import java.util.UUID

object FunctionSpeedClicker {

    private val redItem by lazy {
        ItemBuilder(Material.STAINED_GLASS_PANE).apply {
            damage = 14
            name = "&c&l点我会失败".colored()
        }.build()
    }

    private val greenItem by lazy {
        ItemBuilder(Material.STAINED_GLASS_PANE).apply {
            damage = 13
            name = "&a&l点!".colored()
        }.build()
    }

    fun joinGame(player: Player) {
        val profile = player.profile() ?: return
        if (profile.isInSpeedClickerGame) {
            return
        }
        profile.isInSpeedClickerGame = true
        profile.speedClickerState = SpeedClickerStatus.RED
        profile.speedClickerTask = submit(async = false, delay = getRandomTime()) {
            if (!player.isOnline) {
                cancel()
                return@submit
            }
            profile.speedClickerMillis = System.currentTimeMillis()
            profile.speedClickerState = SpeedClickerStatus.GREEN
            player.playSound(player.location, Sound.BLOCK_NOTE_PLING, 1f, 1f)
        }
    }

    private fun getRandomTime(): Long {
        return (100..180).random().toLong()
    }

    fun turnStateToRed(profile: Profile) {
        val player = profile.player
        for (index in 0..8) {
            player.inventory.setItem(index, redItem)
        }
    }

    fun turnStateToGreen(profile: Profile) {
        val player = profile.player
        for (index in 0..8) {
            player.inventory.setItem(index, greenItem)
        }
    }
}