package net.sakuragame.eternal.kirrajoiner

import net.sakuragame.dungeonsystem.client.api.DungeonClientAPI
import net.sakuragame.dungeonsystem.common.handler.MapRequestHandler
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkitAPI
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

object StoryDungeonCompat {

    @Suppress("SpellCheckingInspection")
    fun join(player: Player): Boolean {
        val serverId = DungeonClientAPI.getClientManager().queryServer("rpg-story") ?: return false
        val isSucc = AtomicBoolean(true)
        DungeonClientAPI.getClientManager().queryDungeon("nergigante_dragon", serverId, LinkedHashSet<Player>().apply {
            add(player)
        }, object : MapRequestHandler() {

            override fun onTimeout(serverID: String) {
                isSucc.set(false)
            }

            override fun onTeleportTimeout(serverID: String) {
                isSucc.set(false)
            }

            override fun handle(serverID: String, mapUUID: UUID) {
                KirraCoreBukkitAPI.teleportPlayerToAnotherServer(serverID, player)
            }
        })
        return isSucc.get()
    }
}