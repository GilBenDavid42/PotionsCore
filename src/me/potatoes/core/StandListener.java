package me.potatoes.core;

import org.bukkit.Bukkit;
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
                e.getClick() == ClickType.LEFT) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.plugin, () -> {

                final ItemStack currentItem = e.getCurrentItem();
                final ItemStack heldItem = e.getCursor().clone();
                e.setCursor(currentItem);
                e.getClickedInventory().setItem(e.getSlot(), heldItem);
                ((Player) e.getView().getPlayer()).updateInventory();
            }, 1L);
        }
    }

}
