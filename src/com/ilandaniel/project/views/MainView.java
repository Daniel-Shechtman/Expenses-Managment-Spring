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
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    loginScreen = new LoginScreen();
                    loginScreen.setViewModel(viewModel);
                    loginScreen.init();
                    loginScreen.start();

                    currentScreen = loginScreen;
                }
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
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Helper.showMessage(title, msg);
                }
            });
        }
    }

    @Override
    public void showScreen(String name) {
        currentScreen.dispose2();

        switch (name) {
            case "Home":
                homeScreen = new HomeScreen();
                homeScreen.setViewModel(viewModel);
                homeScreen.init();
                homeScreen.start();

                currentScreen = homeScreen;
                break;

            case "Category":
                categoryScreen = new CategoryScreen();
                categoryScreen.setViewModel(viewModel);
                categoryScreen.init();
                categoryScreen.start();

                currentScreen = categoryScreen;
                break;

            case "AddExpense":
                expenseScreen = new AddExpenseScreen();
                expenseScreen.setViewModel(viewModel);
                expenseScreen.init();
                expenseScreen.start();

                currentScreen = expenseScreen;
                break;

            case "Login":
                loginScreen = new LoginScreen();
                loginScreen.setViewModel(viewModel);
                loginScreen.init();
                loginScreen.start();

                currentScreen = loginScreen;
                break;

            case "Register":
                registerScreen = new RegisterScreen();
                registerScreen.setViewModel(viewModel);
                registerScreen.init();
                registerScreen.start();

                currentScreen = registerScreen;
                break;

            case "Reports":
                reportsScreen = new ReportsScreen();
                reportsScreen.setViewModel(viewModel);
                reportsScreen.init();
                reportsScreen.start();

                currentScreen = reportsScreen;
                break;
        }
    }

    @Override
    public void deleteCategory(String categoryName) {
        if (SwingUtilities.isEventDispatchThread()) {
            categoryScreen.deleteCategory(categoryName);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    categoryScreen.deleteCategory(categoryName);
                }
            });
        }

    }

    @Override
    public void addCategory(String categoryName) {
        if (SwingUtilities.isEventDispatchThread()) {
            categoryScreen.addCategory(categoryName);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    categoryScreen.addCategory(categoryName);
                }
            });
        }

    }


    @Override
    public void loadTableExpenses(List<Expense> expenses) {
        if (SwingUtilities.isEventDispatchThread()) {
            homeScreen.loadTableExpenses(expenses);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    homeScreen.loadTableExpenses(expenses);
                }
            });
        }

    }

    public void setCategoryName(String name) {
        if (SwingUtilities.isEventDispatchThread()) {
            homeScreen.setCategoryName(name);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    homeScreen.setCategoryName(name);
                }
            });
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
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (currentScreen == expenseScreen) {
                        expenseScreen.loadCategoriesNamesIntoComboBox(names);
                    } else {
                        categoryScreen.loadCategoriesNamesIntoComboBox(names);
                    }
                }
            });
        }


    }

    @Override
    public void loadResportsExpenses(List<Expense> expensesList) {
        if (SwingUtilities.isEventDispatchThread()) {
            if (currentScreen == reportsScreen) {
                reportsScreen.loadTableExpenses(expensesList);
            }
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (currentScreen == reportsScreen) {
                        reportsScreen.loadTableExpenses(expensesList);
                    }
                }
            });
        }
    }
}
