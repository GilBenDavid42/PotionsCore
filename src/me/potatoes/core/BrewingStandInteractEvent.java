package me.potatoes.core;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BrewingStandInteractEvent extends InventoryClickEvent implements Cancellable {
    public BrewingStandInteractEvent(InventoryClickEvent original) {
        super(original.getView(), original.getSlotType(), original.getSlot(), original.getClick(), original.getAction());
    }
}
