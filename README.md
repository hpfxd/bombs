https://hpfxd.tooger.xyz/bombs
[![CircleCI](https://circleci.com/gh/hpfxd/bombs.svg?style=svg)](https://circleci.com/gh/hpfxd/bombs)
### Welcome to Bombs.
Bombs is a Spigot plugin that will add 'bombs' to the game.  
Once you right click the bomb it will explode after a set cooldown.  
It will destroy any blocks in a set radius of the explosion.
### Features
* Free!
* Unlimited bombs
* Customizable
* Supports [WorldGuard](https://dev.bukkit.org/projects/worldguard)
* Open source


### Default configuration
```yaml
Bombs:
  example: # Can be any name.
    enabled: true # Is the bomb enabled?
    radius: 3 # Radius of the blocks that will be broken.
    delay: 3 # Delay in seconds until the bomb explodes.
    speed: 3 # Speed that the bomb should be thrown at.
    dropMode: inventory # Puts the items into the player's inventory, does not drop anything.
  largerExample:
    enabled: true # Is enabled
    radius: 5 # Will break all blocks within 5 meters of the explosion.
    delay: 3 # Waits 3 seconds until exploding.
    speed: 5 # The bomb will be thrown at the player's direction multiplied by five.
    dropMode: drop # Drops the items onto the ground.
```
### Discord server
[![Instant invite](https://discordapp.com/api/guilds/452697743396175873/widget.png)](https://discord.gg/3bVRcru)