package me.potatoes.core;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.charset.Charset;

public class Core extends JavaPlugin {

    public static Core plugin;
    public static NamespacedKey key;
    public static StringArrayItemTagType stringArrayItemTagType = new StringArrayItemTagType(Charset.forName("utf-16"));

    public Core() {

        this.plugin = this;
        this.key = new NamespacedKey(this, "CustomPotions");
    }


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new StandListener(), this);
        getServer().getPluginManager().registerEvents(new PotionsListener(), this);
        BrewingRecipe godRecipe = new BrewingRecipe(new ItemStack(Material.DIAMOND), new ItemStack(Material.GOLDEN_APPLE), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), false, 200);
        BrewingRecipe.addRecipe(godRecipe);
        BrewingRecipe goldRecipe = new BrewingRecipe(new ItemStack(Material.GLOWSTONE_DUST), new ItemStack(Material.IRON_INGOT), new ItemStack(Material.GOLD_INGOT), false, 600);
        BrewingRecipe.addRecipe(goldRecipe);
        CustomPotion.registerEffect("sethalf", entity -> {
            if(entity instanceof Damageable){
                Damageable damageableEntity = (Damageable) entity;
                damageableEntity.setHealth(1);
            }
        });

        CustomPotion potion = new CustomPotion();
        try {
            potion.addEffect("sethalf");
        } catch (NonExistentEffectException e) {
            System.out.println(e.getMessage());
        }

        BrewingRecipe potionRecipe = new BrewingRecipe(new ItemStack(Material.BRICK), new ItemStack(Material.GLASS_BOTTLE), potion.getPotion(true), false, 100);
        BrewingRecipe.addRecipe(potionRecipe);

    }

    @Override
    public void onDisable() {

    }
}
