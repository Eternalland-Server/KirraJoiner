package net.sakuragame.eternal.kirrajoiner

import net.sakuragame.eternal.kirrajoiner.function.speedclicker.FunctionSpeedClicker
import net.sakuragame.eternal.kirrajoiner.function.speedclicker.sub.SpeedClickerStatus.*
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.service.PlatformExecutor

class Profile(val player: Player) {

    var speedClickerTask: PlatformExecutor.PlatformTask? = null

    var isInSpeedClickerGame = false

    var speedClickerState = NONE
        set(value) {
            field = value
            when (value) {
                NONE -> player.reset(closeInventory = true, clearInventory = true)
                RED -> FunctionSpeedClicker.turnStateToRed(this)
                GREEN -> FunctionSpeedClicker.turnStateToGreen(this)
            }
        }

    var speedClickerMillis = System.currentTimeMillis()

    companion object {

        private val profiles = mutableMapOf<String, Profile>()

        fun Player.profile() = profiles.values.find { it.player.uniqueId == uniqueId }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun e(e: PlayerJoinEvent) {
            val player = e.player
            profiles[player.name] = Profile(player)
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun e(e: PlayerKickEvent) {
            dataRecycle(e.player)
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun e(e: PlayerQuitEvent) {
            dataRecycle(e.player)
        }

        private fun dataRecycle(player: Player) {
            player.profile()?.apply {
                drop()
            }
        }
    }

    fun drop() {
        speedClickerTask?.cancel()
        KirraJoiner.baffle.reset(player.name)
        profiles.remove(player.name)
    }
}