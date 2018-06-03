package xyz.tooger.bombs.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.tooger.bombs.Bomb;

public class PreBombEvent extends Event implements Cancellable {
    private boolean isCancelled = false;
    private Player player;
    private Bomb bomb;
    private static final HandlerList handlers = new HandlerList();

    public PreBombEvent(Player player, Bomb bomb) {
        this.player = player;
        this.bomb = bomb;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Bomb getBomb() {
        return bomb;
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
