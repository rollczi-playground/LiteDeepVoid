/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.view;

import dev.rollczi.litedeepvoid.config.plugin.PluginConfig;
import dev.rollczi.litedeepvoid.deepvoid.DeepVoid;
import dev.rollczi.litedeepvoid.scheduler.Scheduler;
import dev.rollczi.litedeepvoid.view.deepvoid.VoidView;
import dev.rollczi.litedeepvoid.view.deepvoid.VoidViewCreator;
import dev.rollczi.litedeepvoid.view.deepvoid.VoidViewModel;
import org.bukkit.Server;

public class ViewRegistry {

    public final ViewsManager<VoidViewModel, VoidView> VOID;

    public ViewRegistry(DeepVoid deepVoid, Scheduler scheduler, Server server, PluginConfig pluginConfig) {
        VOID = new ViewsManager<>(new VoidViewCreator(scheduler, deepVoid, server, pluginConfig));
    }

}
