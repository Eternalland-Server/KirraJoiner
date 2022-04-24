package net.sakuragame.eternal.kirrajoiner.function.speedclicker

import net.sakuragame.eternal.kirrajoiner.Profile
import net.sakuragame.eternal.kirrajoiner.Profile.Companion.profile
import net.sakuragame.eternal.kirrajoiner.function.speedclicker.sub.SpeedClickerStatus.*
import net.sakuragame.eternal.kirrajoiner.getPing
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.asLangText

object FunctionSpeedClickerListener {

    @SubscribeEvent(EventPriority.LOWEST)
    fun e(e: PlayerInteractEvent) {
        if (e.hand != EquipmentSlot.HAND) {
            return
        }
        if (e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK || e.action == Action.PHYSICAL) {
            return
        }
        val player = e.player
        val profile = player.profile() ?: return
        if (!profile.isInSpeedClickerGame) {
            return
        }
        when (profile.speedClickerState) {
            GREEN -> {
                player.sendMessage(player.asLangText("player-speed-clicker-result", (System.currentTimeMillis() - profile.speedClickerMillis) - player.getPing()))
                profile.speedClickerState = NONE
                profile.isInSpeedClickerGame = false
            }
            RED -> {
                player.sendMessage(player.asLangText("player-speed-clicker-failed"))
                profile.reset()
            }
            NONE -> return
        }
    }

    private fun Profile.reset() {
        speedClickerTask?.cancel()
        speedClickerTask = null
        speedClickerState = NONE
        isInSpeedClickerGame = false
    }
}