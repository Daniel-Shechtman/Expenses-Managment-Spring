package com.ilandaniel.project.interfaces;

/**
 * Interface that represents all the screens.
 */
public interface IScreen {
    /**
     * Method that sets the viewModel at specific screen
     */
    void setViewModel(IViewModel viewModel);

    /**
     * Init a specific screen
     */
    void init();

    /**
     * start a specific screen
     */
    void start();

    /**
     * dispose a specific screen
     */
    void dispose();

}
