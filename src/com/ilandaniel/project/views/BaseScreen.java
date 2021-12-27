package com.ilandaniel.project.views;

import com.ilandaniel.project.interfaces.IScreen;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;

public abstract class BaseScreen extends JFrame implements IScreen {
    protected IViewModel viewModel;

    @Override
    public void dispose2() {
        System.out.println("dispose");
        this.dispose();
    }
}
