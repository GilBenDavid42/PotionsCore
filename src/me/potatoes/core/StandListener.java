package me.potatoes.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;


public class StandListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void potionItemPlacer(final InventoryClickEvent e) {
        if (e.getClickedInventory() != null &&
                e.getClickedInventory().getType() == InventoryType.BREWING &&
                e.getClick() == ClickType.LEFT &&
                !(e instanceof BrewingStandInteractEvent) &&
                e.getSlot() != 4) {
            final ItemStack currentItem = e.getCurrentItem();
            final ItemStack heldItem = e.getCursor().clone();
            Bukkit.broadcastMessage(ChatColor.GREEN + "replacing " + currentItem.getType() + " with " + heldItem.getType());
            e.setCancelled(true);
            BrewingStandInteractEvent newEvent = new BrewingStandInteractEvent(e);
            Bukkit.getServer().getPluginManager().callEvent(newEvent);
            if(!newEvent.isCancelled()) {
                newEvent.getView().getPlayer().setItemOnCursor(currentItem);
                newEvent.getClickedInventory().setItem(e.getSlot(), heldItem);
            }
        }
    }

}
