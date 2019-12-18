package net.runelite.client.plugins.nmzbot;

import com.google.inject.Provides;

import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.plugins.itemstats.stats.Stats;
import net.runelite.client.plugins.nmzbot.utils.ExtUtils;

@PluginDescriptor(
        name = "Nmz bot",
        description = "Sips prayer pot and overload for you",
        tags = {"nmz", "prayer", "overload", "bot"},
        type = PluginType.EXTERNAL
)
@Slf4j
public class NmzBot extends Plugin
{
    @Inject
    private Client client;
    @Inject
    private ConfigManager configManager;
    @Inject
    private NmzBotConfig config;
    @Inject
    private KeyManager keyManager;
    @Inject
    private MenuManager menuManager;
    @Inject
    private ItemManager itemManager;
    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1);
    private ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 25, TimeUnit.SECONDS, queue,
            new ThreadPoolExecutor.DiscardPolicy());

    private static final int[] NMZ_MAP_REGION = {9033};
    private static final int[] PRAYER_POTIONS = {143, 141, 139, 2434};
    private static final int[] OVERLOAD_POTIONS = {11733, 11732, 11731, 11730};

    private static int MAXIMUM_PRAYER_DOSE = 33;

    private boolean drinkingOverload = false; //to prevent spam clicking

    @Provides
    NmzBotConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(NmzBotConfig.class);
    }

    @Override
    protected void startUp()
    {
        ExtUtils.setClient(client);
        ExtUtils.setConfigManager(configManager);
    }

    @Subscribe
    private void onGameTick(GameTick tick)
    {
        if(!isInNightmareZone()) return;

        if(getHitpoints() == Stats.HITPOINTS.getMaximum(client) && !drinkingOverload)
        {
            drinkOverloadPotion();
        } else if(getHitpoints() < Stats.HITPOINTS.getMaximum(client)) {
            drinkingOverload = false; //finished drinking overload
        }

        if(getPrayerPoints() <= Stats.PRAYER.getMaximum(client) - MAXIMUM_PRAYER_DOSE)
        {
            drinkPrayerPotion();
        }
    }

    private void drinkPotion(int[] potionIds)
    {
        WidgetItem potion = ExtUtils.getFirstItem(potionIds, client);
        if(potion == null) return;
        Rectangle potionBounds = potion.getCanvasBounds();
        ExtUtils.clickBounds(potionBounds);
    }

    private void drinkPrayerPotion()
    {
        drinkPotion(PRAYER_POTIONS);
    }

    private void drinkOverloadPotion()
    {
        drinkPotion(OVERLOAD_POTIONS);
        drinkingOverload = true; //started drinking overload
    }

    private boolean isInNightmareZone()
    {
        return Arrays.equals(client.getMapRegions(), NMZ_MAP_REGION);
    }

    private int getPrayerPoints()
    {
        return Stats.PRAYER.getValue(client);
    }

    private int getHitpoints()
    {
        return Stats.HITPOINTS.getValue(client);
    }
}
