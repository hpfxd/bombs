package xyz.tooger.bombs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Bombs extends JavaPlugin {
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new BombListener(), this);
        this.getConfig().options().copyDefaults(true);
    }

    static FileConfiguration getCfg() {
        return new Bombs().getConfig();
    }
}
