package xyz.tooger.bombs.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.tooger.bombs.Bomb;

import java.util.List;

public class BombEvent extends Event implements Cancellable {
    private boolean isCancelled = false;
    private Player player;
    private List<Location> locs;
    private Bomb bomb;
    private static final HandlerList handlers = new HandlerList();

    public BombEvent(Player player, List<Location> locs, Bomb bomb) {
        this.player = player;
        this.locs = locs;
        this.bomb = bomb;
    }

    public Player getPlayer() {
        return this.player;
    }

    public List<Location> getLocations() {
        return this.locs;
    }

    public Bomb getBomb() {
        return this.bomb;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
