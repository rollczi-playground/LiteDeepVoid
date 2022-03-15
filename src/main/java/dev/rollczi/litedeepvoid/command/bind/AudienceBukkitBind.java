/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.command.bind;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.bind.LiteBind;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;

public class AudienceBukkitBind implements LiteBind {

    private final BukkitAudiences bukkitAudiences;

    public AudienceBukkitBind(BukkitAudiences bukkitAudiences) {
        this.bukkitAudiences = bukkitAudiences;
    }

    @Override
    public Object apply(LiteInvocation invocation) {
        return bukkitAudiences.sender((CommandSender) invocation.sender().getSender());
    }

}
