package xyz.tooger.bombs;

import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Bombs extends JavaPlugin {
	private static Bombs instance;

    public void onEnable() {
    	instance = this;
        this.getServer().getPluginManager().registerEvents(new BombListener(), this);
        this.getCommand("bomb").setExecutor(new BombCommand());
        this.getConfig().options().copyDefaults(true);

        Metrics metrics = new Metrics(this);
    }

    static FileConfiguration getCfg() {
        return getInstance().getConfig();
    }

    public static Bombs getInstance() {
    	return instance;
    }

    public static Bomb getBomb(String key) {
        return new Bomb() {
            @Override
            public String getName() {
                return key;
            }

            @Override
            public boolean isEnabled() {
                return getCfg().getBoolean("Bombs." + key + ".enabled");
            }

            @Override
            public int getRadius() {
                return getCfg().getInt("Bombs." + key + ".radius");
            }

            @Override
            public int getDelay() {
                return getCfg().getInt("Bombs." + key + ".delay");
            }

            @Override
            public int getSpeed() {
                return getCfg().getInt("Bombs." + key + ".speed");
            }

            @Override
            public int getCooldown() {
                return getCfg().getInt("Bombs." + key + ".cooldown");
            }

            @Override
            public String getDropMode() {
                return getCfg().getString("Bombs." + key + ".dropMode");
            }
        };
    }
}
