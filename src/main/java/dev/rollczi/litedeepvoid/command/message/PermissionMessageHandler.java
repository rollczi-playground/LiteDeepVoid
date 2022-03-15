/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.command.message;

import dev.rollczi.litecommands.valid.messages.LiteMessage;
import dev.rollczi.litecommands.valid.messages.MessageInfoContext;
import dev.rollczi.litedeepvoid.config.plugin.PluginConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import panda.utilities.text.Joiner;

public class PermissionMessageHandler implements LiteMessage {

    private final PluginConfig pluginConfig;

    public PermissionMessageHandler(PluginConfig pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    @Override
    public String message(MessageInfoContext messageInfoContext) {
        Component component = pluginConfig.invalidPermission
                .replaceText(builder -> builder
                        .match("\\$\\{permissions}")
                        .replacement(Joiner.on(", ").join(messageInfoContext.getMissingPermissions()).toString()));

        return MiniMessage.get().serialize(component);
    }

}
