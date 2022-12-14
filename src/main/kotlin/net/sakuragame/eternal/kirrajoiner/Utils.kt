package net.sakuragame.eternal.kirrajoiner

import net.sakuragame.eternal.kirrajoiner.function.speedclicker.FunctionSpeedClickerGUI
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun String.colored() = ChatColor.translateAlternateColorCodes('&', this)!!

fun Player.reset(closeInventory: Boolean, clearInventory: Boolean) {
    (this as CraftPlayer).handle.absorptionHearts = 0f
    saturation = 12.8f
    maximumNoDamageTicks = 20
    fireTicks = 0
    fallDistance = 0.0f
    level = 0
    inventory.heldItemSlot = 0
    exp = 0.0f
    allowFlight = false
    canPickupItems = true
    gameMode = GameMode.ADVENTURE
    walkSpeed = 0.2f
    updateInventory()
    if (clearInventory) {
        inventory.clear()
        inventory.armorContents = null
        itemOnCursor = ItemStack(Material.AIR)
    }
    if (closeInventory) {
        closeInventory()
    }
    activePotionEffects.forEach {
        removePotionEffect(it.type)
    }
    inventory.setItem(8, FunctionSpeedClickerGUI.joinItem)
}

fun Player.getPing(): Int {
    return (this as CraftPlayer).handle.ping
}