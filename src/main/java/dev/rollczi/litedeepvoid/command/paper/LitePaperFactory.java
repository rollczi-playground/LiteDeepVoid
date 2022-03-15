/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.command.paper;

import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.LiteFactory;
import dev.rollczi.litecommands.bind.basic.OriginalSenderBind;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public class LitePaperFactory {

    private LitePaperFactory() {}

    public static LiteCommandsBuilder builder(Server server, BukkitAudiences audiences, MiniMessage miniMessage, String fallbackPrefix) {
        return LiteFactory.builder()
                .bind(Server.class, server)
                .bind(CommandSender.class, new OriginalSenderBind())
                .platform(new LitePaperPlatformManager(server, miniMessage, fallbackPrefix, audiences));
    }

}
