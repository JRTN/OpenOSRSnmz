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
}
