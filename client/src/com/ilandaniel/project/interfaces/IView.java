package com.ilandaniel.project.interfaces;

import com.ilandaniel.project.classes.Expense;

import java.util.List;

public interface IView {
    void init();

    void start();

    void setViewModel(IViewModel viewModel);

    void showMessage(String title, String msg);

    void showScreen(String name);

    void deleteCategory(String categoryName);

    void addCategory(String categoryName);


    void loadTableExpenses(List<Expense> expenses);

    void loadCategoriesNames(List<String> names);


    void loadReportsExpenses(List<Expense> expensesList);
}
