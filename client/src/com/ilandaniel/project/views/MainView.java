package com.ilandaniel.project.views;

import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IScreen;
import com.ilandaniel.project.interfaces.IView;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;
import java.util.List;

/**
 * implements the IView interface-in charge of all the screens in the project
 */
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

    /**
     * the first screen will be the login screen, and we are checking if the thread is of kind
     * EDT
     */
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

    /**
     * showing all the messages that passed from the viewModel to the current logged-in user
     *
     * @param title
     * @param msg
     */
    @Override
    public void showMessage(String title, String msg) {
        if (SwingUtilities.isEventDispatchThread()) {
            Helper.showMessage(title, msg);
        } else {
            SwingUtilities.invokeLater(() -> Helper.showMessage(title, msg));
        }
    }

    /**
     * receiving screen name and initializing that screen
     *
     * @param name
     */
    @Override
    public void showScreen(String name) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("I AM BEFORE DISPOSE");
                currentScreen.dispose();
                System.out.println("I AM AFTER DISPOSE");

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
        });

    }

    /**
     * if the viewModel passes the category name after deleting the category, screen will refresh
     * the category list
     *
     * @param categoryName
     */
    @Override
    public void deleteCategory(String categoryName) {
        if (SwingUtilities.isEventDispatchThread()) {
            categoryScreen.deleteCategory(categoryName);
        } else {
            SwingUtilities.invokeLater(() -> categoryScreen.deleteCategory(categoryName));
        }

    }

    /**
     * if the viewModel passes the category name after adding new category, screen will refresh
     * the category list
     *
     * @param categoryName
     */
    @Override
    public void addCategory(String categoryName) {
        if (SwingUtilities.isEventDispatchThread()) {
            categoryScreen.addCategory(categoryName);
        } else {
            SwingUtilities.invokeLater(() -> categoryScreen.addCategory(categoryName));
        }

    }

    /**
     * after a new expense is added the home screen will refresh the expense list
     *
     * @param expenses
     */
    @Override
    public void loadTableExpenses(List<Expense> expenses) {
        if (SwingUtilities.isEventDispatchThread()) {
            homeScreen.loadTableExpenses(expenses);
        } else {
            SwingUtilities.invokeLater(() -> homeScreen.loadTableExpenses(expenses));
        }

    }

    /**
     * when the user enters the expense screen,the screen will present all the category
     * names
     *
     * @param names
     */
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

    /**
     * after the user asked for specific expenses the view presents those expenses
     *
     * @param expensesList
     */
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
