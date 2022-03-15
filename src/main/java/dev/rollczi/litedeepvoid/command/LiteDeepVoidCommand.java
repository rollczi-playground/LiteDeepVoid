/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.command;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litedeepvoid.config.ConfigurationManager;
import dev.rollczi.litedeepvoid.config.plugin.PluginConfig;
import dev.rollczi.litedeepvoid.deepvoid.DeepVoid;
import dev.rollczi.litedeepvoid.view.ViewRegistry;
import dev.rollczi.litedeepvoid.view.deepvoid.VoidViewModel;
import net.dzikoysk.cdn.CdnException;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import panda.std.Blank;
import panda.std.Result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Section(route = "deepvoid", aliases = { "void", "otchlan" })
@Permission("dev.rollczi.deepvoid")
public class LiteDeepVoidCommand {

    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("mm:ss:SSS");
    private static final Pattern TIME_PATTERN = Pattern.compile("\\$\\{time}");

    private final PluginConfig config;
    private final DeepVoid deepVoid;
    private final ViewRegistry viewRegistry;

    public LiteDeepVoidCommand(PluginConfig config, DeepVoid deepVoid, ViewRegistry viewRegistry) {
        this.config = config;
        this.deepVoid = deepVoid;
        this.viewRegistry = viewRegistry;
    }

    @Execute
    public void deepVoidGui(Audience audience, Player player) {
        if (deepVoid.isDisabled()) {
            audience.sendMessage(config.voidDisabledInfo);
            return;
        }

        if (deepVoid.isClosed()) {
            long milliseconds = deepVoid.getTimeToOpen() * 50;
            Date date = new Date(milliseconds);

            audience.sendMessage(config.voidClosedInfo.replaceText(builder -> builder.match(TIME_PATTERN).replacement(TIME_FORMAT.format(date))));
            return;
        }

        VoidViewModel model = new VoidViewModel(deepVoid.getVoidItems());

        viewRegistry.VOID.show(player, model);
    }

    @Execute(route = "help")
    public void help(Audience audience) {
        for (Component component : config.help) {
            audience.sendMessage(component);
        }
    }

    @Execute(route = "on")
    @Permission("dev.rollczi.deepvoid.on")
    public void enable(Audience audience) {
        if (deepVoid.isEnabled()) {
            audience.sendMessage(config.voidAlreadyEnabled);
            return;
        }

        audience.sendMessage(config.voidEnabled);
        deepVoid.enable();
    }

    @Permission("dev.rollczi.deepvoid.off")
    @Execute(route = "off")
    public void disable(Audience audience) {
        if (deepVoid.isDisabled()) {
            audience.sendMessage(config.voidAlreadyDisabled);
            return;
        }

        audience.sendMessage(config.voidDisabled);
        deepVoid.disable();
    }

    @Permission("dev.rollczi.deepvoid.reload")
    @Execute(route = "reload")
    public void reload(ConfigurationManager configurationManager, Audience audience) {
        Result<Blank, CdnException> result = configurationManager.loadConfigs();

        if (result.isOk()) {
            audience.sendMessage(config.voidReload);
            return;
        }

        CdnException error = result.getError();
        Component infoError = config.voidReloadError.replaceText(builder -> builder.replacement("${stacktrace}").replacement(error.toString()));

        audience.sendMessage(infoError);
        error.printStackTrace();
    }

    @Permission("dev.rollczi.deepvoid.clear")
    @Execute(route = "czysc")
    public void clean(Audience audience) {
        this.deepVoid.enable();
        this.deepVoid.updateTimeToOpen(time -> config.timeClear);
        audience.sendMessage(config.voidClean);
    }

}