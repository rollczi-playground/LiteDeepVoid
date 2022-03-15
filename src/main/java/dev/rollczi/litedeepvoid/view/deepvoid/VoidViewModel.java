/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.view.deepvoid;

import dev.rollczi.litedeepvoid.view.ViewModel;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VoidViewModel implements ViewModel {

    private final List<ItemStack> itemStacks;

    public VoidViewModel(List<ItemStack> itemStacks) {
        this.itemStacks = new ArrayList<>(itemStacks);
    }

    public List<ItemStack> getItemStacks() {
        return Collections.unmodifiableList(itemStacks);
    }

}
