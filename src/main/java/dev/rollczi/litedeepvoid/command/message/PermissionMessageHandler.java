/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.command.message;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.valid.messages.LiteMessage;
import dev.rollczi.litecommands.valid.messages.MessageInfoContext;
import dev.rollczi.litecommands.valid.messages.PermissionMessage;
import dev.rollczi.litedeepvoid.config.plugin.PluginConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import panda.utilities.text.Joiner;

import java.util.List;

public class PermissionMessageHandler implements PermissionMessage {

    private final PluginConfig pluginConfig;

    public PermissionMessageHandler(PluginConfig pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    @Override
    public String message(LiteInvocation invocation, List<String> permissions) {
        Component component = pluginConfig.invalidPermission
                .replaceText(builder -> builder
                        .match("\\$\\{permissions}")
                        .replacement(Joiner.on(", ").join(permissions).toString()));

        return MiniMessage.miniMessage().serialize(component);
    }

}
