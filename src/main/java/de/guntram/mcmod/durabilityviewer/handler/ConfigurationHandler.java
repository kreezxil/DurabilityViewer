package de.guntram.mcmod.durabilityviewer.handler;

import de.guntram.mcmod.durabilityviewer.client.gui.Corner;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import java.io.File;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler
{
    private static ConfigurationHandler instance;
    
    private Configuration config;
    private TextFormatting tooltipColor;
    private String configFileName;
    
    private int corner=0;
    private int color= 5;
    private boolean effectDuration;
    private int minPercent = 10;
    private int minDurability = 100;
    private boolean showPlayerServerName;
    private boolean useCustomSound;
    
    public static ConfigurationHandler getInstance() {
        if (instance==null)
            instance=new ConfigurationHandler();
        return instance;
    }
    
    public void load(final File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            configFileName=configFile.getPath();
            loadConfig();
        }
    }
    
    public static String getConfigFileName() {
        return getInstance().configFileName;
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase("durabilityviewer")) {
            loadConfig();
        }
    }
    
    private void loadConfig() {
        corner=config.getInt("HUD Corner", Configuration.CATEGORY_CLIENT, corner, 0, 3, "Corner 0 to 3 - bottom right, bottom left, top right, top left");
        color=config.getInt("Tooltip Color", Configuration.CATEGORY_CLIENT, color, 0, 15, "Minecraft Color 0 .. 15");
        effectDuration=config.getBoolean("Effect Duration", Configuration.CATEGORY_CLIENT, true, "Show effect durations");
        minPercent = config.getInt("Minimum Percent", Configuration.CATEGORY_CLIENT, minPercent, 1, 100, "Play sound when durability below X percent");
        minDurability = config.getInt("Minimum Durability", Configuration.CATEGORY_CLIENT, minDurability, 1, 1500, "Play sound when durability below X");
        showPlayerServerName = config.getBoolean("Set window title", Configuration.CATEGORY_CLIENT, true, "Set window title to player and server name");
        // useCustomSound = config.getBoolean("Use custom sound", Configuration.CATEGORY_CLIENT, false, "Use your own warning sound. You need to create your own custom.ogg in the mod folder");
        
        tooltipColor=TextFormatting.fromColorIndex(color);
        if (config.hasChanged())
            config.save();
    }
    
    public static TextFormatting getTooltipColor() {
        return getInstance().tooltipColor;
    }
    
    public static Corner getCorner() {
        return Corner.values()[getInstance().corner];
    }
    
    public static Configuration getConfig() {
        return getInstance().config;
    }
    
    public static boolean showEffectDuration() {
        return getInstance().effectDuration;
    }
    
    public static int getMinPercent() {
        return getInstance().minPercent;
    }
    
    public static int getMinDurability() {
        return getInstance().minDurability;
    }
    
    public static boolean showPlayerServerName() {
        return getInstance().showPlayerServerName;
    }
    
    public static boolean useCustomSound() {
        // return getInstance().useCustomSound;
        return false;
    }
}
