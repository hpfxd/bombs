package xyz.tooger.bombs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONObject;
import xyz.tooger.bombs.events.BombEvent;
import xyz.tooger.bombs.events.PreBombEvent;

import java.util.ArrayList;
import java.util.List;

public class BombListener implements Listener {
    private JSONObject cooldowns = new JSONObject();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getPlayer().hasPermission("bombs.use")) return;
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            try {
                // Variables
                final ConfigurationSection cfg = Bombs.getCfg().getConfigurationSection("Bombs");
                final Player player = event.getPlayer();
                final ItemStack item = new ItemStack(player.getItemInHand().getType());
                final List<String> lore = player.getItemInHand().getItemMeta().getLore();

                item.setAmount(1);

                if (lore.size() != 0 && lore.get(0).endsWith(" bomb")) { // is bomb
                    final String type = lore.get(0)  // checks what type the bomb is so that
                            .split(" ")[0]        // we may use it later
                            .replace(ChatColor.GRAY + "", "");
                    if (cfg.contains(type)) { // bomb exists
                        event.setCancelled(true);
                        // Options
                        final ConfigurationSection bombCfg = cfg.getConfigurationSection(type);
                        final boolean enabled = bombCfg.getBoolean("enabled");
                        final int radius = bombCfg.getInt("radius");
                        final int delay = bombCfg.getInt("delay");
                        final int speed = bombCfg.getInt("speed");
                        final int cooldown = bombCfg.getInt("cooldown");
                        final String dropMode = bombCfg.getString("dropMode");
                        Bomb bomb = new Bomb() {
                            @Override public String getName() { return type; }
                            @Override public boolean isEnabled() { return enabled; }
                            @Override public int getRadius() { return radius; }
                            @Override public int getDelay() { return delay; }
                            @Override public int getSpeed() { return speed; }
                            @Override public int getCooldown() { return cooldown; }
                            @Override public String getDropMode() { return dropMode; }
                        };
                        PreBombEvent pbe = new PreBombEvent(player, bomb);
                        if (cooldowns.has(player.getUniqueId().toString())) {
                            player.sendMessage(ChatColor.GRAY + "You are on cooldown for " + ChatColor.RED + cooldowns.getInt(player.getUniqueId().toString()) + ChatColor.GRAY + " seconds.");
                            pbe.setCancelled(true);
                        }
                        Bukkit.getPluginManager().callEvent(pbe);

                        if (pbe.isCancelled()) return;
                        if (!enabled) return;
                        this.removeOneFromInv(player);
                        cooldowns.put(player.getUniqueId().toString(), cooldown);
                        final Item dropped = player.getWorld().dropItem(player.getLocation(), item);
                        dropped.setVelocity(player.getLocation().getDirection().multiply(speed).normalize());
                        dropped.setPickupDelay(9001); // pickup delay is over nine thousand.
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                cooldowns.put(player.getUniqueId().toString(), cooldowns.getInt(player.getUniqueId().toString()) - 1);
                                if (cooldowns.getInt(player.getUniqueId().toString()) < 1) {
                                    cooldowns.remove(player.getUniqueId().toString());
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Bombs.getInstance(), 20, 20);

                        Bombs.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Bombs.getInstance(), () -> {
                            dropped.remove();
                            final List<Location> locs = new ArrayList<>();
                            for (Location loc : util.getLocsNear(dropped.getLocation(), radius)) {
                                if (util.canBreak(loc, player)) {
                                    locs.add(loc);
                                }
                            }

                            BombEvent be = new BombEvent(player, locs, bomb);
                            Bukkit.getPluginManager().callEvent(be);
                            if (!be.isCancelled()) {
                                for (Location loc : locs) {
                                    if (dropMode.equals("inventory")) {
                                        Block block = loc.getBlock();
                                        if (!block.getType().equals(Material.AIR)) {
                                            player.getInventory().addItem(new ItemStack(block.getType()));
                                            block.setType(Material.AIR);
                                        }
                                    } else {
                                        loc.getBlock().breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
                                    }
                                }
                            }
                        }, 20 * delay);
                    }

                }
            } catch (NullPointerException ignored) {}
        }
    }

    private void removeOneFromInv(Player player) { // remove one of the item in hand from the player.
        ItemStack item = player.getItemInHand();
        item.setAmount(item.getAmount()-1);
        player.setItemInHand(item);
    }
}
