/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.litedeepvoid.view;

public interface ViewCreator<M extends ViewModel, V extends View<M>> {

    V create(M model);

}
