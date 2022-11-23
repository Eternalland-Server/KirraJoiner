package net.sakuragame.eternal.kirrajoiner

import org.bukkit.command.CommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper

@Suppress("SpellCheckingInspection")
@CommandHeader(name = "KirraJoiner", aliases = ["join"])
object Commands {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { sender, _, _ ->
            KirraJoiner.conf.reload()
            sender.sendMessage("&c[System] &7完成.".colored())
        }
    }
}