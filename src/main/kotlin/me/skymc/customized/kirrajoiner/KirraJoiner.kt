package me.skymc.customized.kirrajoiner

import net.sakuragame.kirracore.bukkit.KirraCoreBukkitAPI
import org.bukkit.event.player.PlayerPortalEvent
import taboolib.common.platform.Plugin
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common5.Baffle
import java.util.concurrent.TimeUnit

object KirraJoiner : Plugin() {

    val baffle by lazy {
        Baffle.of(2, TimeUnit.SECONDS)
    }

    @SubscribeEvent
    fun e(e: PlayerPortalEvent) {
        if (!baffle.hasNext(e.player.name)) {
            return
        }
        baffle.next()
        KirraCoreBukkitAPI.teleportPlayerToHudServer(e.player)
    }
}