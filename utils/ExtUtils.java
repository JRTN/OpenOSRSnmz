package net.runelite.client.plugins.nmzbot.utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.stretchedmode.StretchedModeConfig;

@Slf4j
public class ExtUtils
{

    @Setter(AccessLevel.PUBLIC)
    private static Client client;
    @Setter(AccessLevel.PUBLIC)
    private static ConfigManager configManager;

    public static List<WidgetItem> getInventoryItems(int[] itemIds)
    {
        Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);

        ArrayList<Integer> itemIDs = new ArrayList<>();

        for (int i : itemIds)
        {
            itemIDs.add(i);
        }

        List<WidgetItem> listToReturn = new ArrayList<>();

        for (WidgetItem item : inventoryWidget.getWidgetItems())
        {
            if (itemIDs.contains(item.getId()))
            {
                listToReturn.add(item);
            }
        }

        return listToReturn;
    }

    public static WidgetItem getFirstInventoryItem(int[] itemIds)
    {
        List<WidgetItem> items = getInventoryItems(itemIds);
        if(items.isEmpty()) return null;
        return items.get(0);
    }


    public static void clickInventoryItem(int[] itemIds) {
        WidgetItem item = getFirstInventoryItem(itemIds);
        if(item == null) return;
        Rectangle itemBounds = item.getCanvasBounds();
        clickBounds(itemBounds);
    }

    public static void clickBounds(Rectangle rectangle)
    {
        if (rectangle == null)
        {
            return;
        }

        final net.runelite.api.Point cp = getClickPoint(rectangle);

        if (cp.getX() >= 1 && cp.getY() >= 1)
        {
            leftClick(cp.getX(), cp.getY());
        }
    }

    private static void leftClick(int x, int y)
    {
        final double scalingFactor = configManager.getConfig(StretchedModeConfig.class).scalingFactor();
        if (client.isStretchedEnabled())
        {
            if (client.getMouseCanvasPosition().getX() != x ||
                    client.getMouseCanvasPosition().getY() != y)
            {
                moveMouse(x, y);
            }

            double scale = 1 + (scalingFactor / 100);

            MouseEvent mousePressed =
                    new MouseEvent(client.getCanvas(), 501, System.currentTimeMillis(), 0, (int) (client.getMouseCanvasPosition().getX() * scale), (int) (client.getMouseCanvasPosition().getY() * scale), 1, false, 1);
            client.getCanvas().dispatchEvent(mousePressed);
            MouseEvent mouseReleased =
                    new MouseEvent(client.getCanvas(), 502, System.currentTimeMillis(), 0, (int) (client.getMouseCanvasPosition().getX() * scale), (int) (client.getMouseCanvasPosition().getY() * scale), 1, false, 1);
            client.getCanvas().dispatchEvent(mouseReleased);
            MouseEvent mouseClicked =
                    new MouseEvent(client.getCanvas(), 500, System.currentTimeMillis(), 0, (int) (client.getMouseCanvasPosition().getX() * scale), (int) (client.getMouseCanvasPosition().getY() * scale), 1, false, 1);
            client.getCanvas().dispatchEvent(mouseClicked);
        }
        else
        {
            if (client.getMouseCanvasPosition().getX() != x ||
                    client.getMouseCanvasPosition().getY() != y)
            {
                moveMouse(x, y);
            }
            MouseEvent mousePressed = new MouseEvent(client.getCanvas(), 501, System.currentTimeMillis(), 0, client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY(), 1, false, 1);
            client.getCanvas().dispatchEvent(mousePressed);
            MouseEvent mouseReleased = new MouseEvent(client.getCanvas(), 502, System.currentTimeMillis(), 0, client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY(), 1, false, 1);
            client.getCanvas().dispatchEvent(mouseReleased);
            MouseEvent mouseClicked = new MouseEvent(client.getCanvas(), 500, System.currentTimeMillis(), 0, client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY(), 1, false, 1);
            client.getCanvas().dispatchEvent(mouseClicked);
        }
    }

    private static void moveMouse(int x, int y)
    {
        MouseEvent mouseEntered = new MouseEvent(client.getCanvas(), 504, System.currentTimeMillis(), 0, x, y, 0, false);
        client.getCanvas().dispatchEvent(mouseEntered);
        MouseEvent mouseExited = new MouseEvent(client.getCanvas(), 505, System.currentTimeMillis(), 0, x, y, 0, false);
        client.getCanvas().dispatchEvent(mouseExited);
        MouseEvent mouseMoved = new MouseEvent(client.getCanvas(), 503, System.currentTimeMillis(), 0, x, y, 0, false);
        client.getCanvas().dispatchEvent(mouseMoved);
    }

    private static Point getClickPoint(Rectangle rect)
    {
        double scalingFactor = configManager.getConfig(StretchedModeConfig.class).scalingFactor();

        int rand = (Math.random() <= 0.5) ? 1 : 2;
        int x = (int) (rect.getX() + (rand * 3) + rect.getWidth() / 2);
        int y = (int) (rect.getY() + (rand * 3) + rect.getHeight() / 2);

        double scale = 1 + (scalingFactor / 100);

        if (client.isStretchedEnabled())
        {
            return new net.runelite.api.Point((int) (x * scale), (int) (y * scale));
        }

        return new Point(x, y);
    }
}
