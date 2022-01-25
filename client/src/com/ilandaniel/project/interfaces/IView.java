package com.ilandaniel.project.interfaces;

import com.ilandaniel.project.classes.Expense;

import java.util.List;

/**
 * main IVIEW interface.
 * contains all gui methods
 */
public interface IView {
    void init();

    void start();

    void setViewModel(IViewModel viewModel);

    /**
     * show message on screen
     *
     * @param title
     * @param msg
     */
    void showMessage(String title, String msg);

    /**
     * open new screen by screen name
     *
     * @param name
     */
    void showScreen(String name);

    /**
     * delete category by category name
     *
     * @param categoryName
     */
    void deleteCategory(String categoryName);

    /**
     * add new category
     *
     * @param categoryName
     */
    void addCategory(String categoryName);

    /**
     * load the expeneses table with expenses
     *
     * @param expenses
     */
    void loadTableExpenses(List<Expense> expenses);

    /**
     * loadcategories names in combobox
     *
     * @param names
     */
    void loadCategoriesNames(List<String> names);

    /**
     * load reports in report expenses table.
     *
     * @param expensesList
     */
    void loadReportsExpenses(List<Expense> expensesList);
}
