package net.runelite.client.plugins.nmzbot;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("nmzbot")
public interface NmzBotConfig extends Config {

    @ConfigItem(
            keyName = "prayerThreshold",
            name = "Prayer points to drink at",
            description = "Set this between 10 and 60 for best results",
            titleSection = "config",
            position = 0
    )
    default int prayerThreshold()
    {
        return 60;
    }

    @ConfigItem(
            keyName = "guzzleRockCake",
            name = "Guzzle rock cake",
            description = "Make sure rock cake is set to guzzle in menuentryswapper",
            titleSection = "config",
            position = 1
    )
    default boolean guzzleRockCake() { return false; }

    @ConfigItem(
            keyName = "rockCakeThreshold",
            name = "Rock cake threshold",
            description = "The HP at which to use rock cake",
            titleSection = "config",
            position = 2
    )
    default int rockCakeThreshold() { return 2; }
}
