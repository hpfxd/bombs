package xyz.tooger.bombs;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.GlobalRegionManager;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

class util {
    static boolean canBreak(Location loc, Player player) {
        if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") == null) return true;
        return checkWorldGuard(loc, player, "BLOCK-BREAK");
    }

    private static WorldGuardPlugin getWorldGuard() {
            Plugin worldguard = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
            if (worldguard instanceof WorldGuardPlugin && worldguard.isEnabled())
                return (WorldGuardPlugin) worldguard;
        return null;
    }

    private static boolean checkWorldGuard(Location l, Player p, String fs) {
        if (util.getWorldGuard() != null) {
            GlobalRegionManager grm = util.getWorldGuard().getGlobalRegionManager();

            if (grm == null)
                return true;

            StateFlag f = null;
            for (Flag<?> df : DefaultFlag.flagsList)
                if (fs.equalsIgnoreCase(df.getName()))
                    f = (StateFlag) df;

            if (f != null && f.equals(DefaultFlag.BUILD)) {
                if (!grm.canBuild(p, l)) {
                    return false;
                }
            } else if (!grm.allows(f, l, util.getWorldGuard().wrapPlayer(p))) {
                return false;
            }
        }
        return true;
    }

    static List<Location> getLocsNear(Location sL, int locRad) {
        List<Location> locations = new ArrayList<Location>();
        int r = locRad - 1;
        int start = r / 2;
        sL.setX(sL.getX() - start);
        sL.setY(sL.getY() - start);
        sL.setZ(sL.getZ() - start);
        for (int x = 0; x < locRad; x++)
            for (int y = 0; y < locRad; y++)
                for (int z = 0; z < locRad; z++)
                    if ((!(x == 0 && y == 0 && z == 0)) && (!(x == r && y == 0 && z == 0)) && (!(x == 0 && y == r && z == 0)) && (!(x == 0 && y == 0 && z == r)) && (!(x == r && y == r && z == 0))
                            && (!(x == 0 && y == r && z == r)) && (!(x == r && y == 0 && z == r)) && (!(x == r && y == r && z == r)))
                        locations.add(new Location(sL.getWorld(), sL.getX() + x, sL.getY() + y, sL.getZ() + z));
        return locations;
    }
}
