package com.ilandaniel.project;

import com.ilandaniel.project.interfaces.IModel;
import com.ilandaniel.project.interfaces.IView;
import com.ilandaniel.project.interfaces.IViewModel;
import com.ilandaniel.project.models.Model;
import com.ilandaniel.project.viewmodels.ViewModel;
import com.ilandaniel.project.views.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        IModel model = new Model();
        IViewModel viewModel = new ViewModel();
        IView view = new MainView();
        SwingUtilities.invokeLater(() -> {
            view.init();
            view.start();
        });
        view.setViewModel(viewModel);
        viewModel.setView(view);
        viewModel.setModel(model);
    }
}
