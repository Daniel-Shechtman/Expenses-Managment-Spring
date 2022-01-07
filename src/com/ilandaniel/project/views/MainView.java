package com.ilandaniel.project.views;

import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IScreen;
import com.ilandaniel.project.interfaces.IView;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;
import java.util.List;

public class MainView implements IView {

    IViewModel viewModel;

    IScreen currentScreen = null;

    CategoryScreen categoryScreen;
    AddExpenseScreen expenseScreen;
    LoginScreen loginScreen;
    RegisterScreen registerScreen;
    HomeScreen homeScreen;
    ReportsScreen reportsScreen;

    public MainView() {

    }

    @Override
    public void init() {

    }

    @Override
    public void start() {
        if (SwingUtilities.isEventDispatchThread()) {
            loginScreen = new LoginScreen();
            loginScreen.setViewModel(viewModel);
            loginScreen.init();
            loginScreen.start();

            currentScreen = loginScreen;
        } else {
            SwingUtilities.invokeLater(() -> {
                loginScreen = new LoginScreen();
                loginScreen.setViewModel(viewModel);
                loginScreen.init();
                loginScreen.start();

                currentScreen = loginScreen;
            });

        }
    }

    @Override
    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void showMessage(String title, String msg) {
        if (SwingUtilities.isEventDispatchThread()) {
            Helper.showMessage(title, msg);
        } else {
            SwingUtilities.invokeLater(() -> Helper.showMessage(title, msg));
        }
    }

    @Override
    public void showScreen(String name) {
        currentScreen.dispose2();

        switch (name) {
            case "Home" -> {
                homeScreen = new HomeScreen();
                homeScreen.setViewModel(viewModel);
                homeScreen.init();
                homeScreen.start();
                currentScreen = homeScreen;
            }
            case "Category" -> {
                categoryScreen = new CategoryScreen();
                categoryScreen.setViewModel(viewModel);
                categoryScreen.init();
                categoryScreen.start();
                currentScreen = categoryScreen;
            }
            case "AddExpense" -> {
                expenseScreen = new AddExpenseScreen();
                expenseScreen.setViewModel(viewModel);
                expenseScreen.init();
                expenseScreen.start();
                currentScreen = expenseScreen;
            }
            case "Login" -> {
                loginScreen = new LoginScreen();
                loginScreen.setViewModel(viewModel);
                loginScreen.init();
                loginScreen.start();
                currentScreen = loginScreen;
            }
            case "Register" -> {
                registerScreen = new RegisterScreen();
                registerScreen.setViewModel(viewModel);
                registerScreen.init();
                registerScreen.start();
                currentScreen = registerScreen;
            }
            case "Reports" -> {
                reportsScreen = new ReportsScreen();
                reportsScreen.setViewModel(viewModel);
                reportsScreen.init();
                reportsScreen.start();
                currentScreen = reportsScreen;
            }
        }
    }

    @Override
    public void deleteCategory(String categoryName) {
        if (SwingUtilities.isEventDispatchThread()) {
            categoryScreen.deleteCategory(categoryName);
        } else {
            SwingUtilities.invokeLater(() -> categoryScreen.deleteCategory(categoryName));
        }

    }

    @Override
    public void addCategory(String categoryName) {
        if (SwingUtilities.isEventDispatchThread()) {
            categoryScreen.addCategory(categoryName);
        } else {
            SwingUtilities.invokeLater(() -> categoryScreen.addCategory(categoryName));
        }

    }


    @Override
    public void loadTableExpenses(List<Expense> expenses) {
        if (SwingUtilities.isEventDispatchThread()) {
            homeScreen.loadTableExpenses(expenses);
        } else {
            SwingUtilities.invokeLater(() -> homeScreen.loadTableExpenses(expenses));
        }

    }

    public void loadCategoriesNames(List<String> names) {
        if (SwingUtilities.isEventDispatchThread()) {
            if (currentScreen == expenseScreen) {
                expenseScreen.loadCategoriesNamesIntoComboBox(names);
            } else {
                categoryScreen.loadCategoriesNamesIntoComboBox(names);
            }
        } else {
            SwingUtilities.invokeLater(() -> {
                if (currentScreen == expenseScreen) {
                    expenseScreen.loadCategoriesNamesIntoComboBox(names);
                } else {
                    categoryScreen.loadCategoriesNamesIntoComboBox(names);
                }
            });
        }


    }

    @Override
    public void loadReportsExpenses(List<Expense> expensesList) {
        if (SwingUtilities.isEventDispatchThread()) {
            if (currentScreen == reportsScreen) {
                reportsScreen.loadTableExpenses(expensesList);
            }
        } else {
            SwingUtilities.invokeLater(() -> {
                if (currentScreen == reportsScreen) {
                    reportsScreen.loadTableExpenses(expensesList);
                }
            });
        }
    }
}
