package net.runelite.client.plugins.nmzbot;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("nmzbot")
public interface NmzBotConfig extends Config {

    @ConfigItem(
            keyName = "randLow",
            name = "Minimum MS Delay",
            description = "Dont set this too high.",
            titleSection = "config",
            position = 0
    )
    default int randLow()
    {
        return 60;
    }

    @ConfigItem(
            keyName = "randLower",
            name = "Maximum MS Delay",
            description = "Dont set this too high.",
            titleSection = "config",
            position = 1
    )
    default int randHigh()
    {
        return 80;
    }
}
