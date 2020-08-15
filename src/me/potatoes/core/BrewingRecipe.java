package me.potatoes.core;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BrewingRecipe {
    private static List<BrewingRecipe> recipes = new ArrayList<>();
    private ItemStack ingredient;
    private ItemStack toModify;
    private ItemStack toMake;
    private boolean useMetaData;
    private int brewingTime;

    public BrewingRecipe(ItemStack ingredient, ItemStack toModify, ItemStack toMake, boolean useMetadata, int brewingTime)
    {
        this.ingredient = ingredient;
        this.toModify = toModify;
        this.toMake = toMake;
        this.useMetaData = useMetadata;
        this.brewingTime = brewingTime;
    }

    public static void addRecipe(BrewingRecipe recipe)
    {
        recipes.add(recipe);
    }

    public int getBrewingTime() {
        return brewingTime;
    }

    public void setBrewingTime(int brewingTime) {
        this.brewingTime = brewingTime;
    }

    public ItemStack getIngredient() {
        return ingredient;
    }

    public ItemStack getToModify() {
        return toModify;
    }

    public ItemStack getToMake() {
        return toMake;
    }

    public void setToMake(ItemStack toMake) {
        this.toMake = toMake;
    }

    public boolean isUseMetaData() {
        return useMetaData;
    }

    public void setIngredient(ItemStack ingredient) {
        this.ingredient = ingredient;
    }

    public void setToModify(ItemStack toModify) {
        this.toModify = toModify;
    }

    public void setUseMetaData(boolean useMetaData) {
        this.useMetaData = useMetaData;
    }

    public static BrewingRecipe isRecipe(BrewerInventory inv)
    {
        for(BrewingRecipe recipe : BrewingRecipe.recipes)
        {
            if(inv.getIngredient() != null && inv.getIngredient().getType() == recipe.getIngredient().getType())
            {
                boolean hasItems = false;
                boolean allAcceptable = true;
                for(int i = 0; i < 3; i++)
                {
                    if(inv.getItem(i).getType() == recipe.getToModify().getType())
                    {
                        hasItems = ((recipe.isUseMetaData() && inv.getItem(i).getItemMeta() == recipe.getToModify().getItemMeta()) || !recipe.isUseMetaData() );
                    }else if(inv.getItem(i).getType() != Material.AIR){
                        allAcceptable = false;
                    }
                }
                if(allAcceptable && hasItems)
                {
                    new BrewRunnable(inv, recipe);
                    return recipe;
                }
            }
        }
        return null;
    }


}

class BrewRunnable extends BukkitRunnable
{
    private BrewerInventory inventory;
    private BrewingRecipe recipe;
    private BrewingStand stand;
    private int time;
    private int startTime;

    public BrewRunnable(BrewerInventory inventory, BrewingRecipe recipe) {
        this.inventory = inventory;
        this.recipe = recipe;
        this.time = recipe.getBrewingTime();
        this.startTime = recipe.getBrewingTime();
        this.stand = inventory.getHolder();
        runTaskTimer(Core.plugin, 0L, 1L);

    }

    @Override
    public void run() {
        if (time==0)
        {
            inventory.setIngredient(new ItemStack(Material.AIR));
            for(int i = 0; i < 3 ; i++)
            {
                if((recipe.isUseMetaData() && inventory.getItem(i).getItemMeta() == recipe.getToModify().getItemMeta())
                        || ((!recipe.isUseMetaData()) && recipe.getToModify().getType() == inventory.getItem(i).getType()) )
                {
                    inventory.setItem(i, recipe.getToMake());
                }
            }
            cancel();
        }else if(!((recipe.isUseMetaData() && inventory.getIngredient().getItemMeta() == recipe.getIngredient().getItemMeta())
                || ((!recipe.isUseMetaData()) && recipe.getIngredient().getType() == inventory.getIngredient().getType())))
        {
            stand.setBrewingTime(400); //Reseting everything
            cancel();
        }else
        {
            boolean hasItems = false;
            boolean allAcceptable = true;
            for(int i = 0; i < 3; i++)
            {
                if(inventory.getItem(i).getType() == recipe.getToModify().getType())
                {
                    hasItems = ((recipe.isUseMetaData() && inventory.getItem(i).getItemMeta() == recipe.getToModify().getItemMeta()) || !recipe.isUseMetaData() );
                }else if(inventory.getItem(i).getType() != Material.AIR){
                    allAcceptable = false;
                }
            }
            if(allAcceptable && hasItems)
            {
                time--;
                int standProgress = (int)((float)time / startTime * 400);
                stand.setBrewingTime(standProgress);
                stand.update();
            }
        }
    }
}
