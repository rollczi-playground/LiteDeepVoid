/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.view.deepvoid;

import dev.rollczi.litedeepvoid.deepvoid.DeepVoid;
import dev.rollczi.litedeepvoid.scheduler.Scheduler;
import dev.rollczi.litedeepvoid.view.ViewCreator;
import dev.rollczi.litedeepvoid.config.plugin.PluginConfig;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.IntStream;

public final class VoidViewCreator implements ViewCreator<VoidViewModel, VoidView> {

    private final Scheduler scheduler;
    private final DeepVoid deepVoid;
    private final Server server;
    private final PluginConfig config;

    public VoidViewCreator(Scheduler scheduler, DeepVoid deepVoid, Server server, PluginConfig config) {
        this.scheduler = scheduler;
        this.deepVoid = deepVoid;
        this.server = server;
        this.config = config;
    }

    @Override
    public VoidView create(VoidViewModel model) {
        List<ItemStack> items = model.getItemStacks();
        int row = (int) Math.min(6, Math.ceil(items.size() / 9.0) + 1);

        PaginatedGui gui = Gui.paginated()
                .title(config.title)
                .rows(row)
                .create();

        IntStream.rangeClosed(2, 9)
                .forEach(col -> gui.setItem(row, col, config.fill.toGuiItem()));
        IntStream.of(1, 9)
                .forEach(col -> gui.setItem(row, col, config.cornerFill.toGuiItem()));

        return new VoidView(server, config, scheduler, deepVoid, gui);
    }

}
