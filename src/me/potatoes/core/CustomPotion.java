package me.potatoes.core;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.nio.charset.Charset;
import java.util.*;

public class CustomPotion {
    private boolean isSplash;
    private static Map<String, CustomEffect> effectMap = new HashMap<>();
    private Set<String> effects;

    public CustomPotion() {
        effects = new HashSet<>();
    }


    public static boolean registerEffect(String effectName, CustomEffect customEffect) {
        boolean result = !effectMap.containsKey(effectName);
        if (result) {
            effectMap.put(effectName, customEffect);
        }
        return result;
    }

    public void addEffect(String effectName) throws NonExistentEffectException {
        if (!effectMap.containsKey(effectName)) {
            throw new NonExistentEffectException(effectName);
        }

        effects.add(effectName);
    }

    public static void applyEffects(String[] effects, Entity entity) {
        if(effects != null)
        {
            for (String effectName : effects) {
                effectMap.get(effectName).affect(entity);
            }
        }
    }

    public ItemStack getPotion(boolean isSplash) {
        ItemStack newPotion = new ItemStack(isSplash ? Material.SPLASH_POTION : Material.POTION);
        ItemMeta potionMeta = newPotion.getItemMeta();
        NamespacedKey key = new NamespacedKey(Core.plugin, "CustomPotions");

        String[] potionData = effects.toArray(new String[effects.size()]);

        potionMeta.getPersistentDataContainer().set(key, new StringArrayItemTagType(Charset.forName("utf-16")), potionData);
        newPotion.setItemMeta(potionMeta);

        return newPotion;
    }
}
