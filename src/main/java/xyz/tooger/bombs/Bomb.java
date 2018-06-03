package xyz.tooger.bombs;

public interface Bomb {
    String getName();
    boolean isEnabled();
    int getRadius();
    int getDelay();
    int getSpeed();
    int getCooldown();
    String getDropMode();
}
