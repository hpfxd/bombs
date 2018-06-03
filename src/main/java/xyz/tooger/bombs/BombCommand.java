package xyz.tooger.bombs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BombCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {
        if (args.length == 0) {
            s.sendMessage(ChatColor.RED + "Bombs by hpfxd and others.");
            return false;
        }
        if (args.length == 1) {
            if (args[0].equals("list")) {
                Set<String> keys = Bombs.getCfg().getConfigurationSection("Bombs").getKeys(false);

                for (String key : keys) {
                    Bomb bomb = Bombs.getBomb(key);
                    List<String> messages = new ArrayList<>();

                    messages.add("&c" + bomb.getName());
                    messages.add("  &bEnabled&7: " + bomb.isEnabled());
                    messages.add("  &bRadius&7: " + bomb.getRadius());
                    messages.add("  &bDelay&7: " + bomb.getDelay());
                    messages.add("  &bSpeed&7: " + bomb.getSpeed());
                    messages.add("  &bCooldown&7: " + bomb.getCooldown());
                    messages.add("  &bDrop mode&7: " + bomb.getDropMode());

                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', String.join("\n", messages)));
                }
                return true;
            }
            return false;
        }
        if (args.length == 3) {
            if (args[0].equals("give")) {
                if (s.hasPermission("bombs.give")) {
                    try {
                        Player player = Bukkit.getPlayer(args[1]);
                        ItemStack item = new ItemStack(Material.FIREBALL);
                        ItemMeta meta = item.getItemMeta();
                        List<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GRAY + args[2] + " bomb");
                        meta.setLore(lore);
                        meta.setDisplayName(ChatColor.RED + args[2] + " bomb!");
                        item.setItemMeta(meta);
                        player.getInventory().addItem(item);
                    } catch (NullPointerException e) {
                        s.sendMessage(ChatColor.RED + "Error processing your command... Are you sure " + args[1] + " is online?");
                    }
                } else {
                    s.sendMessage(ChatColor.RED + "You do not have permission.");
                }
                return true;
            }
            return false;
        }
        return false;
    }
}
