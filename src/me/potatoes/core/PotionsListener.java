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
            NamespacedKey key = new NamespacedKey(Core.plugin, "CustomPotions");
            ItemMeta itemMeta = potion.getItemMeta();
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();
            StringArrayItemTagType stringArrayType = new StringArrayItemTagType(Charset.forName("utf-16"));

            String[] potionData = null;
            if (container.has(key, stringArrayType)) {
                potionData = container.get(key, stringArrayType);
            }

        CustomPotion.applyEffects(potionData, e.getPlayer());
        }
    }

    @EventHandler
    public void onPotionThrow(ProjectileLaunchEvent e)
    {
        if(e.getEntity().getType() == EntityType.SPLASH_POTION)
        {
            ThrownPotion potion = (ThrownPotion) e.getEntity();
            if(e.getEntity().getShooter() instanceof Player)
            {
                NamespacedKey key = new NamespacedKey(Core.plugin, "CustomPotions");
                Player player = (Player)e.getEntity().getShooter();
                ItemStack potionItem = player.getInventory().getItemInMainHand();
                PersistentDataContainer oldData = potionItem.getItemMeta().getPersistentDataContainer();
                StringArrayItemTagType stringArrayType = new StringArrayItemTagType(Charset.forName("utf-16"));
                String[] potionData = null;

                if (oldData.has(key, stringArrayType)) {
                    potionData = oldData.get(key, stringArrayType);
                }
                if(potionData != null)
                {
                    e.getEntity().getPersistentDataContainer().set(key, stringArrayType, potionData);
                }
            }
        }
    }

    @EventHandler
    public void onPotionDispense(BlockDispenseEvent e)
    {
    }


    @EventHandler
    public void onPotionSplash(PotionSplashEvent e)
    {
        NamespacedKey key = new NamespacedKey(Core.plugin, "CustomPotions");
        PersistentDataContainer data = e.getEntity().getPersistentDataContainer();
        String[] potionData = null;
        StringArrayItemTagType stringArrayType = new StringArrayItemTagType(Charset.forName("utf-16"));

        if (data.has(key, stringArrayType)) {
            potionData = data.get(key, stringArrayType);
        }

        for(Entity entity : e.getAffectedEntities())
        {
            CustomPotion.applyEffects(potionData, entity);
        }
    }

}
