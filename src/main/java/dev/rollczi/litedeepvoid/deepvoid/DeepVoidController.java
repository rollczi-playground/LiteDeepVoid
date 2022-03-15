/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.deepvoid;

import dev.rollczi.litedeepvoid.config.plugin.PluginConfig;
import dev.rollczi.litedeepvoid.scheduler.Scheduler;
import dev.rollczi.litedeepvoid.view.ViewsManager;
import dev.rollczi.litedeepvoid.view.deepvoid.VoidView;
import dev.rollczi.litedeepvoid.view.deepvoid.VoidViewModel;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class DeepVoidController {

    private final BukkitAudiences bukkitAudiences;
    private final Server server;
    private final PluginConfig config;
    private final Scheduler scheduler;
    private final DeepVoid deepVoid;
    private final ViewsManager<VoidViewModel, VoidView> voidViewViewsManager;

    public DeepVoidController(BukkitAudiences bukkitAudiences, Server server, PluginConfig config, Scheduler scheduler, DeepVoid deepVoid, ViewsManager<VoidViewModel, VoidView> voidViewViewsManager) {
        this.bukkitAudiences = bukkitAudiences;
        this.server = server;
        this.config = config;
        this.scheduler = scheduler;
        this.deepVoid = deepVoid;
        this.voidViewViewsManager = voidViewViewsManager;
        this.deepVoid.updateTimeToOpen(time -> config.timeClear + config.timeClearDelay);
    }

    public void runClearTask() {
        this.nextTick();
    }

    private void nextTick() {
        if (this.deepVoid.isDisabled()) {
            this.scheduler.runTaskLater(this::nextTick, 1L);
            return;
        }

        Component component = config.messagesInTime.get(this.deepVoid.getTimeToOpen());

        if (component != null) {
            bukkitAudiences.all().sendMessage(component);
        }

        if (this.deepVoid.getTimeToOpen() <= 0) {
            this.clearWorldsAndOpenDeepVoid();
            this.deepVoid.updateTimeToOpen(time -> config.timeClear + config.timeClearDelay);
            this.scheduler.runTaskLater(this::nextTick, 1L);
            return;
        }

        this.deepVoid.updateTimeToOpen(time -> time - 1);
        this.scheduler.runTaskLater(this::nextTick, 1L);
    }

    private void clearWorldsAndOpenDeepVoid() {
        this.deepVoid.open();
        this.deepVoid.resetVoidItems();

        for (World world : this.server.getWorlds()) {
            if (!this.config.worlds.contains(world.getName())) {
                continue;
            }

            ArrayList<ItemStack> items = new ArrayList<>();

            for (Item item : world.getEntitiesByClass(Item.class)) {
                items.add(item.getItemStack());
                item.remove();
            }

            this.deepVoid.addVoidItems(items);

            if (this.config.animals) {
                for (Animals animals : world.getEntitiesByClass(Animals.class)) {
                    animals.remove();
                }
            }

            if (this.config.monsters) {
                for (Monster monster : world.getEntitiesByClass(Monster.class)) {
                    monster.remove();
                }
            }
        }

        this.scheduler.runTaskLater(() -> {
            deepVoid.close();

            for (Player player : server.getOnlinePlayers()) {
                if (voidViewViewsManager.close(player)) {
                    bukkitAudiences.player(player).sendMessage(config.voidClosedForce);
                }
            }

        }, config.timeCloseVoid);
    }

}
