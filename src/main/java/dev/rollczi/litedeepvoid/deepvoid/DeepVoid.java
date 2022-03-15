/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.deepvoid;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.LongFunction;

public class DeepVoid {

    private final List<ItemStack> voidItems = new ArrayList<>();
    private final Set<Consumer<List<ItemStack>>> updateListeners = new HashSet<>();
    private boolean closed = true;
    private boolean enabled = true;
    private long timeToOpen = 0;

    public boolean isClosed() {
        return closed;
    }

    public boolean isOpen() {
        return closed;
    }

    public void open() {
        this.closed = false;
    }

    public void close() {
        this.closed = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDisabled() {
        return !enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public long getTimeToOpen() {
        return timeToOpen;
    }

    public void updateTimeToOpen(LongFunction<Long> update) {
        this.timeToOpen = update.apply(timeToOpen);
    }

    public void resetVoidItems() {
        this.voidItems.clear();
    }

    public void addVoidItems(List<ItemStack> itemsFromVoid) {
        this.voidItems.addAll(itemsFromVoid);
    }

    public void addUpdateListener(Consumer<List<ItemStack>> listener) {
        this.updateListeners.add(listener);
    }

    private void callUpdate() {
        for (Consumer<List<ItemStack>> listener : updateListeners) {
            listener.accept(this.getVoidItems());
        }
    }

    public List<ItemStack> getVoidItems() {
        return Collections.unmodifiableList(voidItems);
    }

}
