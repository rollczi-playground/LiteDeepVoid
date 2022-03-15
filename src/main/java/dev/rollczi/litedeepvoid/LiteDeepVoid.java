/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.valid.ValidationInfo;
import dev.rollczi.litedeepvoid.command.LiteDeepVoidCommand;
import dev.rollczi.litedeepvoid.command.bind.AudienceBukkitBind;
import dev.rollczi.litedeepvoid.command.message.InvalidUseMessageHandler;
import dev.rollczi.litedeepvoid.command.message.PermissionMessageHandler;
import dev.rollczi.litedeepvoid.command.paper.LitePaperFactory;
import dev.rollczi.litedeepvoid.config.ConfigurationManager;
import dev.rollczi.litedeepvoid.command.bind.PlayerBind;
import dev.rollczi.litedeepvoid.config.plugin.PluginConfig;
import dev.rollczi.litedeepvoid.deepvoid.DeepVoid;
import dev.rollczi.litedeepvoid.deepvoid.DeepVoidController;
import dev.rollczi.litedeepvoid.scheduler.Scheduler;
import dev.rollczi.litedeepvoid.scheduler.SchedulerBukkit;
import dev.rollczi.litedeepvoid.view.ViewRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class LiteDeepVoid extends JavaPlugin {

    private Scheduler scheduler;
    private BukkitAudiences audience;
    private ConfigurationManager configurationManager;
    private DeepVoid deepVoid;
    private DeepVoidController deepVoidController;
    private ViewRegistry viewRegistry;
    private LiteCommands liteCommands;

    @Override
    public void onEnable() {
        this.scheduler = new SchedulerBukkit(this);
        this.audience = BukkitAudiences.create(this);

        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        this.configurationManager.loadConfigs();

        this.deepVoid = new DeepVoid();
        this.viewRegistry = new ViewRegistry(deepVoid, scheduler, this.getServer(), configurationManager.getPluginConfig());
        this.deepVoidController = new DeepVoidController(audience, this.getServer(), configurationManager.getPluginConfig(), scheduler, deepVoid, viewRegistry.VOID);

        this.liteCommands = LitePaperFactory.builder(this.getServer(), audience, MiniMessage.get(), "deep-void")
                .message(ValidationInfo.INVALID_USE, new InvalidUseMessageHandler(configurationManager.getPluginConfig()))
                .message(ValidationInfo.NO_PERMISSION, new PermissionMessageHandler(configurationManager.getPluginConfig()))

                .bind(Player.class, new PlayerBind())
                .bind(Audience.class, new AudienceBukkitBind(audience))
                .bind(ConfigurationManager.class, () -> configurationManager)
                .bind(PluginConfig.class,         () -> configurationManager.getPluginConfig())
                .bind(DeepVoid.class,             () -> deepVoid)
                .bind(DeepVoidController.class,   () -> deepVoidController)
                .bind(ViewRegistry.class,         () -> viewRegistry)
                .bind(AudienceProvider.class,     () -> audience)
                .command(LiteDeepVoidCommand.class)
                .register();

        this.deepVoidController.runClearTask();
    }

    @Override
    public void onDisable() {
        this.liteCommands.getPlatformManager().unregisterCommands();
    }

    public DeepVoid getDeepVoid() {
        return deepVoid;
    }

    public ViewRegistry getViewRegistry() {
        return viewRegistry;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
