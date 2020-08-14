package me.potatoes.core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    public static Core plugin;

    public Core() {
        this.plugin = this;
    }


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new StandListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
