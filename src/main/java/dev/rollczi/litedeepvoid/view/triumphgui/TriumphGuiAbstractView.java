/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.view.triumphgui;

import dev.rollczi.litedeepvoid.view.View;
import dev.rollczi.litedeepvoid.view.ViewModel;
import dev.triumphteam.gui.guis.BaseGui;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class TriumphGuiAbstractView<M extends ViewModel, G extends BaseGui> implements View<M> {

    protected final G baseGui;
    protected final Server server;
    protected final List<Runnable> closeListeners = new ArrayList<>();
    protected final List<Consumer<M>> changeListeners = new ArrayList<>();

    protected TriumphGuiAbstractView(G baseGui, Server server) {
        this.baseGui = baseGui;
        this.baseGui.setCloseGuiAction(event -> callCloseGuiListeners());
        this.server = server;
    }

    @Override
    public void show(Player player) {
        baseGui.open(player);
    }

    @Override
    public void close(Player player) {
        baseGui.close(player);
    }

    @Override
    public void addCloseGuiListener(Runnable runnable) {
        closeListeners.add(runnable);
    }

    protected void callCloseGuiListeners() {
        for (Runnable listener : closeListeners) {
            listener.run();
        }
    }

    @Override
    public void addChangeModelListener(Consumer<M> listener) {
        changeListeners.add(listener);
    }

    protected void callChangeModelListeners(M model) {
        for (Consumer<M> changeListener : changeListeners) {
            changeListener.accept(model);
        }
    }

}
