package org.srino.nakedprotect;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NakedProtect extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean isNaked(Player player) {
        int k = 0;
        @Nullable ItemStack @NotNull [] armorContents = player.getInventory().getArmorContents();
        for (ItemStack itemStack : armorContents) {
            if (itemStack == null) {
                k++;
                continue;
            }
            if (itemStack.getType() == Material.ELYTRA) k++;
        }
        return k == 4;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) && (!(event.getEntity() instanceof Player))) return;
        if (event.isCancelled()) return;
        Player player = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (isNaked(damager)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Du bist Naked!");
            return;
        }
        if (isNaked(player)) {
            event.setCancelled(true);
        damager.sendMessage(ChatColor.RED + "Dieser Spieler ist Naked!");
        }
    }
}
