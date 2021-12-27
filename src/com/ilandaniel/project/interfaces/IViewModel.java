package com.ilandaniel.project.interfaces;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.dtos.ExpenseDTO;

public interface IViewModel {
    void setModel(IModel model);

    void setView(IView view);

    void showScreen(String name);

    //Categories methods
    void addCategory(Category category);

    void deleteCategory(String categoryName);

    void getAllCategories();

    //Home methods
    void initTableExpenses(int id);

    void getCategoryNameById(int id);

    //Login methods
    void loginUser(AccountLoginDTO client);

    //Add expense methods
    void addNewExpense(ExpenseDTO expenseDTO);

    void getCategoryIdByName(ExpenseDTO expenseDTO);

    //Register methods
    void createAccount(AccountRegisterDTO client);

    void getReport(String fromDate,String toDate);

    void logout();
}
