package me.potatoes.core;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.projectiles.ProjectileSource;

import java.nio.charset.Charset;

public class PotionsListener implements Listener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == Material.POTION) {
            ItemStack potion = e.getItem();
            ItemMeta itemMeta = potion.getItemMeta();
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();

            String[] potionData = null;
            if (container.has(Core.key, Core.stringArrayItemTagType)) {
                potionData = container.get(Core.key, Core.stringArrayItemTagType);
            }

            CustomPotion.applyEffects(potionData, e.getPlayer());
        }
    }

    @EventHandler
    public void onPotionThrow(ProjectileLaunchEvent e) {
        if (e.getEntity().getType() == EntityType.SPLASH_POTION) {
            ThrownPotion potion = (ThrownPotion) e.getEntity();
            if (e.getEntity().getShooter() instanceof Player) {
                Player player = (Player) e.getEntity().getShooter();
                ItemStack potionItem = player.getInventory().getItemInMainHand();
                PersistentDataContainer oldData = potionItem.getItemMeta().getPersistentDataContainer();
                String[] potionData = null;

                if (oldData.has(Core.key, Core.stringArrayItemTagType)) {
                    potionData = oldData.get(Core.key, Core.stringArrayItemTagType);
                }
                if (potionData != null) {
                    e.getEntity().getPersistentDataContainer().set(Core.key, Core.stringArrayItemTagType, potionData);
                }
            }
        }
    }


    @EventHandler
    public void onPotionSplash(PotionSplashEvent e) {
        PersistentDataContainer data = e.getEntity().getPersistentDataContainer();
        String[] potionData = null;

        if (data.has(Core.key, Core.stringArrayItemTagType)) {
            potionData = data.get(Core.key, Core.stringArrayItemTagType);
        }

        for (Entity entity : e.getAffectedEntities()) {
            CustomPotion.applyEffects(potionData, entity);
        }
    }

}
