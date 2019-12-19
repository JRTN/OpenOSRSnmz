package net.runelite.client.plugins.nmzbot;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("nmzbot")
public interface NmzBotConfig extends Config {

    @ConfigItem(
            keyName = "usePrayerPotions",
            name = "Use Prayer Potions",
            description = "Use prayer potions?",
            titleSection = "config",
            position = 0
    )
    default boolean usePrayerPotions() { return true; }

    @ConfigItem(
            keyName = "prayerThreshold",
            name = "Prayer points threshold",
            description = "Set this between 10 and 60 for best results",
            titleSection = "config",
            position = 1
    )
    default int prayerThreshold()
    {
        return 60;
    }

    @ConfigItem(
            keyName = "useAbsorptionPotions",
            name = "use Absorption Potions",
            description = "Use Absorption potions?",
            titleSection = "config",
            position = 2
    )
    default boolean useAbsorptionPotions() { return false; }

    @ConfigItem(
            keyName = "absorptionThreshold",
            name = "Absorption Threshold",
            description = "The number at which to use absorption potions",
            titleSection = "config",
            position = 3
    )
    default int absorptionThreshold() { return 200; }

    @ConfigItem(
            keyName = "guzzleRockCake",
            name = "Guzzle rock cake",
            description = "Make sure rock cake is set to guzzle in menuentryswapper",
            titleSection = "config",
            position = 4
    )
    default boolean guzzleRockCake() { return false; }

    @ConfigItem(
            keyName = "rockCakeThreshold",
            name = "Rock cake threshold",
            description = "The HP at which to use rock cake",
            titleSection = "config",
            position = 5
    )
    default int rockCakeThreshold() { return 2; }
}
