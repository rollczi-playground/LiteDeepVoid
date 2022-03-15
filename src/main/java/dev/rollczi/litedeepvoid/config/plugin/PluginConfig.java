/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.config.plugin;

import com.google.common.collect.ImmutableMap;
import dev.rollczi.litedeepvoid.config.AbstractConfigWithResource;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.entity.Exclude;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PluginConfig extends AbstractConfigWithResource {

    @Exclude
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public PluginConfig(File folder, String child) {
        super(folder, child);

    }

    @Description("# ----- #")
    @Description("#  Gui  #")
    @Description("# ----- #")

    public Component title = miniMessage.deserialize("<dark_gray> » DeepVoid</dark_gray>");

    public int previousSlot = 3;
    public int nextSlot = 7;

    public GuiItemConfig previous = new GuiItemConfig(Material.ARROW, 0, "<green><bold>Poprzednia Strona</bold></green>");
    public GuiItemConfig next = new GuiItemConfig(Material.ARROW, 0, "<green><bold>Następna strona</bold></green>");
    public GuiItemConfig fill = new GuiItemConfig(Material.GLASS, 0, "<gray> </gray>");
    public GuiItemConfig cornerFill = new GuiItemConfig(Material.GLASS, 0, "<gray> x </gray>");

    @Description("# ------ #")
    @Description("#  Void  #")
    @Description("# ------ #")

    public long timeClear = 1200L;
    public long timeClearDelay = 1200L;
    public long timeCloseVoid = 600L;
    public boolean monsters = false;
    public boolean animals = false;
    public List<String> worlds = Arrays.asList("world", "world_nether", "world_the_end");

    @Description("# ---------- #")
    @Description("#  Messages  #")
    @Description("# ---------- #")

    public List<Component> help = Arrays.asList(
        miniMessage.deserialize("<green>-------------------<green>"),
        miniMessage.deserialize("<green>/void<green>"),
        miniMessage.deserialize("<green>/void help<green>"),
        miniMessage.deserialize("<green>/void on<green>"),
        miniMessage.deserialize("<green>/void off<green>"),
        miniMessage.deserialize("<green>/void czysc<green>"),
        miniMessage.deserialize("<green>-------------------<green>")
    );
    public Component invalidUsage = miniMessage.deserialize("<dark_gray> » </dark_gray><red>Poprawne użycie</red><dark_gray> » </dark_gray><gray>${usage}");
    public Component invalidPermission = miniMessage.deserialize("<dark_gray> » </dark_gray><red>Nie posiadasz permisji! <gray>(${permissions})");

    public Component voidEnabled = miniMessage.deserialize("<dark_gray> » </dark_gray><gray>Otchłań została <green>włączona</green>!</gray>");
    public Component voidDisabled = miniMessage.deserialize("<dark_gray> » </dark_gray><gray>Otchłań została <red>wyłączona</red>!</gray>");
    public Component voidAlreadyEnabled = miniMessage.deserialize("<dark_gray> » </dark_gray><red>Otchłań jest już włączona!</red>");
    public Component voidAlreadyDisabled = miniMessage.deserialize("<dark_gray> » </dark_gray><red>Otchłań jest już wyłączona!</red>");
    public Component voidReload = miniMessage.deserialize("<dark_gray> » </dark_gray><green>Plugin został poprawnie przeładowany</green>");
    public Component voidReloadError = miniMessage.deserialize("<dark_gray> » </dark_gray><red>Problem z przeładowaniem pluginu! <hover:show_text:'<red>${stacktrace}'><gray>(stacktrace)</gray><hover></red>");
    public Component voidClean = miniMessage.deserialize("<dark_gray> » </dark_gray><green>Czyszczenie światów!</green>");
    public Component voidClosedInfo = miniMessage.deserialize("<dark_gray> » </dark_gray><red>Otchłań zostanie otwarta za <yellow>${time}!</yellow></red>");
    public Component voidClosedForce = miniMessage.deserialize("<dark_gray> » </dark_gray><red>Otchłań została zamknięta!</red>");
    public Component voidDisabledInfo = miniMessage.deserialize("<dark_gray> » </dark_gray><red>Otchłań została wyłączona przez administratora!</red>");
    public Map<Long, Component> messagesInTime = new ImmutableMap.Builder<Long, Component>()
            .put(1200L, miniMessage.deserialize("<dark_gray> » </dark_gray><gray>Otchłań zbierze itemy za <green>60s</green>!</gray>"))
            .put(600L, miniMessage.deserialize("<dark_gray> » </dark_gray><gray>Otchłań zbierze itemy za <green>30s</green>!</gray>"))
            .put(200L, miniMessage.deserialize("<dark_gray> » </dark_gray><gray>Otchłań zbierze itemy za <green>10s</green>!</gray>"))
            .put(100L, miniMessage.deserialize("<dark_gray> » </dark_gray><gray>Otchłań zbierze itemy za <green>5s</green>!</gray>"))
            .put(80L, miniMessage.deserialize("<dark_gray> » </dark_gray><gray>Otchłań zbierze itemy za <green>4s</green>!</gray>"))
            .put(60L, miniMessage.deserialize("<dark_gray> » </dark_gray><gray>Otchłań zbierze itemy za <green>3s</green>!</gray>"))
            .put(40L, miniMessage.deserialize("<dark_gray> » </dark_gray><gray>Otchłań zbierze itemy za <green>2s</green>!</gray>"))
            .put(20L, miniMessage.deserialize("<dark_gray> » </dark_gray><gray>Otchłań zbierze itemy za <green>1s</green>!</gray>"))
            .put(0L, miniMessage.deserialize("<dark_gray> » </dark_gray><gray>Otchłań została otwarta <hover:show_text:'<gray>Kliknij!</gray>'><click:run_command:'/void'><green>(/void)</green></click></gray>"))
            .build();

}
