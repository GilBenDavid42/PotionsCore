package me.potatoes.core;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.nio.charset.Charset;

public class PotionsListener implements Listener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == Material.POTION) {
            ItemStack potion = e.getItem();
            NamespacedKey key = new NamespacedKey(Core.plugin, "CustomPotions");
            ItemMeta itemMeta = potion.getItemMeta();
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();
            StringArrayItemTagType stringArrayType = new StringArrayItemTagType(Charset.forName("utf-16"));

            String[] potionData;
            if (container.has(key, stringArrayType)) {
                potionData = container.get(key, stringArrayType);
            }


        }
    }
}
