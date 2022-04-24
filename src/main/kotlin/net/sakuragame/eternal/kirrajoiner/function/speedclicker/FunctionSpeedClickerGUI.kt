package net.sakuragame.eternal.kirrajoiner.function.speedclicker

import net.sakuragame.eternal.kirrajoiner.Profile.Companion.profile
import net.sakuragame.eternal.kirrajoiner.colored
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.ui.receptacle.buildReceptacle
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem

object FunctionSpeedClickerGUI {

    val joinItem by lazy {
        ItemBuilder(Material.COOKIE).apply {
            name = "&f点击参加 &6&l亘古快男杯.".colored()
            lore += ""
            lore += "&7???¿¿¿???¿¿¿???¿¿¿???¿¿¿".colored()
            lore += ""
            enchants += Pair(Enchantment.DURABILITY, 10)
            isUnbreakable = true
        }.build()
    }

    private val introItem by lazy {
        ItemBuilder(Material.PAPER).apply {
            name = "&6&l亘古快男杯".colored()
            lore += ""
            lore += "&7&o传说在上古时, 亘古大陆有一位以速度快而著名的神明...".colored()
            lore += "&7&o传闻他干什么都很快, 这不知道为什么引来了许多人的效仿, 与挑战...".colored()
            lore += "&7&o所以...".colored()
            lore += ""
            lore += "&f欢迎来到亘古快男杯. &b&l第一届!".colored()
            lore += "&f挑战规则? 很简单! 只需要在你手上的物品变绿时以最快的速度点击它!".colored()
            lore += "&f这次挑战不但有各大赞助商加盟, 最吸引人的是居然没有任何奖励! 连个毛都没! 真是太有诱惑力了! ".colored()
            lore += ""
            lore += "&e&l樱花大佐: &f我的最快记录是 &6&l1000 &f毫秒, 你肯定不行! ".colored()
            lore += ""
        }.build()
    }

    private val sponsorItem by lazy {
        ItemBuilder(Material.DIAMOND).apply {
            name = "&6&l赞助商列表 &7(以下排名根据赞助金额排序)".colored()
            lore += ""
            lore += "&f* &e捞莱士快餐店".colored()
            lore += "&f* &e阿伟潮牌专卖店".colored()
            lore += "&f* &e筒子便利店".colored()
            lore += "&f* &e华夫饼零食店".colored()
            lore += "&f* &e新坛卤闲蛋牛肉面".colored()
            lore += "&f* &c&m绝望领域服务器&r &7(已倒闭)".colored()
            lore += "&f* &c&m他的世界服务器&r &7(狗都不玩)".colored()
            lore += ""
        }.build()
    }

    private val playItem by lazy {
        ItemBuilder(Material.FEATHER).apply {
            name = "&a&l点击参与".colored()
            lore += ""
            lore += "&f真的么? 真的么? 真的要参与么?".colored()
            lore += "&f好吧... 年轻人, 祝你好运!".colored()
            lore += ""
        }.build()
    }

    private fun openMenu(player: Player) {
        buildReceptacle("&7(这里记得填名字)".colored(), 1) {
            setItem(introItem, 0)
            setItem(sponsorItem, 1)
            setItem(playItem, 8)
            onClick { player, event ->
                event.isCancelled = true
                event.refresh()
                if (event.slot == 8) {
                    player.closeInventory()
                    FunctionSpeedClicker.joinGame(player)
                }
            }
        }.open(player)
    }

    @SubscribeEvent
    fun e(e: PlayerInteractEvent) {
        val item = e.item ?: return
        val player = e.player
        val profile = player.profile() ?: return
        if (item.itemMeta.displayName == joinItem.itemMeta.displayName) {
            if (profile.isInSpeedClickerGame) {
                return
            }
            openMenu(e.player)
        }
    }
}