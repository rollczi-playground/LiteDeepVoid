/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.view;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ViewsManager<M extends ViewModel, V extends View<M>> {

    private final Map<UUID, V> views = new HashMap<>();
    private final ViewCreator<M, V> creator;

    public ViewsManager(ViewCreator<M, V> creator) {
        this.creator = creator;
    }

    public void show(Player player, M model) {
        V view = creator.create(model);
        UUID uuid = player.getUniqueId();

        views.put(uuid, view);
        view.addCloseGuiListener(() -> views.remove(uuid));
        view.addChangeModelListener(changedModel -> views.values().forEach(v -> v.update(changedModel)));
        view.show(player);
        view.update(model);
    }

    public boolean close(Player player) {
        V view = views.remove(player.getUniqueId());

        if (view != null) {
            view.close(player);
            return true;
        }

        return false;
    }

    public void updatePresenters(M model) {
        for (V view : views.values()) {
            view.update(model);
        }
    }

}
