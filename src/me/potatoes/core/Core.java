package me.potatoes.core;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new StandListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
