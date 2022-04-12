package net.sakuragame.eternal.kirrajoiner.function

import com.taylorswiftcn.megumi.uifactory.generate.function.Statements
import net.sakuragame.eternal.justmessage.api.event.notify.NotifyBoxCancelEvent
import net.sakuragame.eternal.justmessage.api.event.notify.NotifyBoxConfirmEvent
import net.sakuragame.eternal.kirrajoiner.KirraJoiner
import net.sakuragame.eternal.kirrajoiner.StoryDungeonCompat
import net.sakuragame.eternal.kirrajoiner.colored
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.sendLang

@Suppress("SpellCheckingInspection")
object FunctionNotifyBox {

    const val KEY = "FULLSCREEN_NOTIFY_BOX"

    private val kickMessages by lazy {
        Statements().apply {
            add("&6&l亘古大陆之超级英雄喷射侠之健康哥哥樱花大佐 &f对你说: ".colored())
            add(" ")
            repeat(20) {
                add("&7???¿¿¿???¿¿¿???¿¿¿???¿¿¿???¿¿¿???¿¿¿???¿¿¿???¿¿¿???¿¿¿???¿¿¿???¿¿¿".colored())
            }
            add(" ")
        }
    }

    @SubscribeEvent
    fun e(e: NotifyBoxConfirmEvent) {
        if (e.key != KEY) return
        val player = e.player
        val isSucc = StoryDungeonCompat.join(player)
        player.closeInventory()
        KirraJoiner.doAnimation(player)
        if (!isSucc) {
            KirraJoiner.baffle.reset(player.name)
            player.sendLang("player-first-teleport-failed")
        }
    }

    @SubscribeEvent
    fun e(e: NotifyBoxCancelEvent) {
        if (e.key != KEY) return
        val player = e.player
        player.kickPlayer(kickMessages.build())
    }
}