package xyz.tooger.bombs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Bombs extends JavaPlugin {
	private static Bombs instance;

    public void onEnable() {
    	this.instance = this;
        this.getServer().getPluginManager().registerEvents(new BombListener(), this);
        this.getConfig().options().copyDefaults(true);
    }

    static FileConfiguration getCfg() {
        return getInstance().getConfig();
    }

    public static Bombs getInstance() {
    	return instance;
    }
}
