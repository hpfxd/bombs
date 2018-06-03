---
title: API Examples
---
### PreBombEvent
```java
public class Example implements Listener {
    @EventHandler
    public void onPreBomb(PreBombEvent event) {
        Player player = event.getPlayer();
        Bomb bomb = event.getBomb();
        if (player.isFlying()) {
            player.sendMessage(ChatColor.RED + "You can't use a " + bomb.getName() + " bomb while flying!");
            event.setCancelled(true);
        }
    }
}
```
### BombEvent
```java
public class Example implements Listener {
    @EventHandler
    public void onBomb(BombEvent event) {
        for (Location loc : event.getLocations()) {
            if (loc.getBlock().getType().equals(Material.DIAMOND_BLOCK)) {
                event.setCancelled(true);
            }
        }
    }
}
```
