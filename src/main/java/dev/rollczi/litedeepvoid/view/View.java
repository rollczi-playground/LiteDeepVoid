/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.view;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface View<M extends ViewModel> {

    void show(Player player);

    void close(Player player);

    void addCloseGuiListener(Runnable runnable);

    void addChangeModelListener(Consumer<M> runnable);

    void update(M viewModel);

}
