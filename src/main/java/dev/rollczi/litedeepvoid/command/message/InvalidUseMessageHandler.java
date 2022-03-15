/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.command.message;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.valid.messages.InvalidUseMessage;
import dev.rollczi.litecommands.valid.messages.LiteMessage;
import dev.rollczi.litecommands.valid.messages.MessageInfoContext;
import dev.rollczi.litedeepvoid.config.plugin.PluginConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import panda.utilities.text.Joiner;

public class InvalidUseMessageHandler implements InvalidUseMessage {

    private final PluginConfig pluginConfig;

    public InvalidUseMessageHandler(PluginConfig pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    @Override
    public String message(LiteInvocation invocation, String useScheme) {
        Component component = pluginConfig.invalidUsage
                .replaceText(builder -> builder
                        .match("\\$\\{usage}")
                        .replacement(useScheme));

        return MiniMessage.miniMessage().serialize(component);
    }

}
