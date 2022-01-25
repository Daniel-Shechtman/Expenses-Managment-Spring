package com.ilandaniel.project.views;

import com.ilandaniel.project.interfaces.IScreen;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;

/**
 * Class that all the screen needs to implement
 */
public abstract class BaseScreen extends JFrame implements IScreen {
    protected IViewModel viewModel;

    /**
     * disposes the current screen
     */
    @Override
    public void dispose() {
        super.dispose();
    }
}
