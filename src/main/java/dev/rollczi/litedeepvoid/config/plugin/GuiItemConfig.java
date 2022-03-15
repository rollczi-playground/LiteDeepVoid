/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.config.plugin;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.guis.GuiItem;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import panda.utilities.StringUtils;

@Contextual
public class GuiItemConfig {

    @Exclude
    private final MiniMessage miniMessage = MiniMessage.get();

    public Material type = Material.STONE;
    public String skull = StringUtils.EMPTY;
    public int durability = 0;
    public Component name = miniMessage.parse("<green>text</green>");

    public GuiItemConfig() {}

    public GuiItemConfig(Material type, int durability, String name) {
        this.type = type;
        this.durability = durability;
        this.name = miniMessage.parse(name);
    }

     public GuiItem toGuiItem(GuiAction<InventoryClickEvent> action) {
         TextComponent RESET = Component.text()
                 .decoration(TextDecoration.ITALIC, false)
                 .build();

        if (skull.isEmpty()) {
            return ItemBuilder.from(new ItemStack(type, 1, (short) durability))
                    .name(RESET.append(name))
                    .asGuiItem(action);
        }

        return ItemBuilder.skull()
                .texture(skull)
                .name(RESET.append(name))
                .asGuiItem(action);
    }

    public GuiItem toGuiItem() {
        return toGuiItem((event) -> event.setCancelled(true));
    }

}
