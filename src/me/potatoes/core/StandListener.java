package me.potatoes.core;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
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
            e.setCancelled(true);
            BrewingStandInteractEvent newEvent = new BrewingStandInteractEvent(e);
            Bukkit.getServer().getPluginManager().callEvent(newEvent);
            if (!newEvent.isCancelled()) {
                if (heldItem.getAmount() == 1 || heldItem == null || heldItem.getType() == Material.AIR || newEvent.getSlot() == 3) {
                    newEvent.getView().getPlayer().setItemOnCursor(currentItem);
                    newEvent.getClickedInventory().setItem(newEvent.getSlot(), heldItem);
                } else if (currentItem == null || currentItem.getType() == Material.AIR) {
                    ItemStack newSlot = heldItem.clone();
                    newSlot.setAmount(1);
                    newEvent.getClickedInventory().setItem(newEvent.getSlot(), newSlot);
                    heldItem.setAmount(heldItem.getAmount() - 1);
                    newEvent.getView().getPlayer().setItemOnCursor(heldItem);
                }

                BrewingRecipe.isRecipe((BrewerInventory) e.getInventory());
            }
        }
    }
}
