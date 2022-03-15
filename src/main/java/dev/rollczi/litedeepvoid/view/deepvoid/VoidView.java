/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.view.deepvoid;

import dev.rollczi.litedeepvoid.config.plugin.PluginConfig;
import dev.rollczi.litedeepvoid.deepvoid.DeepVoid;
import dev.rollczi.litedeepvoid.scheduler.Scheduler;
import dev.rollczi.litedeepvoid.view.View;
import dev.rollczi.litedeepvoid.view.triumphgui.TriumphGuiAbstractView;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.util.ItemNbt;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class VoidView extends TriumphGuiAbstractView<VoidViewModel, PaginatedGui> implements View<VoidViewModel> {

    private final PluginConfig config;
    private final Scheduler scheduler;
    private final DeepVoid deepVoid;
    private final PaginatedGui paginatedGui;

    VoidView(Server server, PluginConfig config, Scheduler scheduler, DeepVoid deepVoid, PaginatedGui paginatedGui) {
        super(paginatedGui, server);
        this.config = config;
        this.scheduler = scheduler;
        this.deepVoid = deepVoid;
        this.paginatedGui = paginatedGui;
    }

    @Override
    public void update(VoidViewModel viewModel) {
        for (GuiItem item : new ArrayList<>(paginatedGui.getPageItems())) {
            paginatedGui.removePageItem(item);
        }

        List<ItemStack> items = viewModel.getItemStacks();
        int size = items.size();

        for (int slot = 0; slot < size; slot++) {
            ItemStack origin = items.get(slot);

            if (origin == null || origin.getType() == Material.AIR) {
                paginatedGui.addItem(config.fill.toGuiItem());
                continue;
            }

            ItemStack clone = origin.clone();

            final int finalSlot = slot;
            paginatedGui.addItem(ItemBuilder.from(clone).asGuiItem(event -> {
                removeTagIfNotNull(event.getCurrentItem()); // double click

                this.scheduler.runTaskLater(() -> {
                    ItemStack changedItem = event.getInventory().getItem(event.getSlot());
                    List<ItemStack> changedItems = new ArrayList<>(items);

                    if (!Objects.equals(origin, changedItem)) {
                        changedItems.set(finalSlot, changedItem);
                    }

                    removeTagIfNotNull(event.getCursor()); // normal or replace click

                    this.deepVoid.resetVoidItems();
                    this.deepVoid.addVoidItems(changedItems);
                    this.callChangeModelListeners(new VoidViewModel(changedItems));
                }, 0L);
            }));
        }

        for (int fillIndex = 0; fillIndex < (size % 9 == 0 ? 0 : 9 - size % 9); fillIndex++) {
            paginatedGui.addItem(config.fill.toGuiItem());
        }

        if (baseGui.getRows() >= 5) {
            int rows =  5 - (int) Math.ceil(items.size() / 9.0) % 5;

            for (int fillIndex = 0; fillIndex < (rows == 5 ? 0 : rows) * 9; fillIndex++) {
                paginatedGui.addItem(config.fill.toGuiItem());
            }
        }

        paginatedGui.setItem(paginatedGui.getRows(), config.previousSlot, paginatedGui.getCurrentPageNum() - 1 == 0
                ? config.fill.toGuiItem()
                : config.previous.toGuiItem(event -> {
                    paginatedGui.previous();
                    event.setCancelled(true);
                    this.update(viewModel);
                }));

        paginatedGui.setItem(paginatedGui.getRows(), config.nextSlot, paginatedGui.getCurrentPageNum() + 1 > paginatedGui.getPagesNum()
                ? config.fill.toGuiItem()
                : config.next.toGuiItem(event -> {
                    paginatedGui.next();
                    event.setCancelled(true);
                    this.update(viewModel);
                }));

        paginatedGui.update();
    }

    private void removeTagIfNotNull(ItemStack itemStack) {
        if (itemStack != null) {
            ItemNbt.removeTag(itemStack, "mf-gui");
        }
    }

}
