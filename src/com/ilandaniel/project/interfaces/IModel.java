package com.ilandaniel.project.interfaces;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.exceptions.ProjectException;

import java.util.List;

public interface IModel {
    //Categories methods
    String addCategory(Category category) throws ProjectException;

    boolean deleteCategory(String categoryName) throws ProjectException;

    List<String> getAllCategories() throws ProjectException;

    //Home methods
    List<Expense> getAllExpenses(int id) throws ProjectException;

    String getCategoryNameById(int id) throws ProjectException;

    //Login methods
    String loginUser(AccountLoginDTO client) throws ProjectException;

    //Add expense methods
    void addNewExpense(ExpenseDTO expenseDTO) throws ProjectException;

    int getCategoryIdByName(String categoryName) throws ProjectException;

    //Register methods
    String createAccount(AccountRegisterDTO client) throws ProjectException;

    List<Expense> getReport(String fromDate,String toDate) throws  ProjectException;

    public void deleteSelected(int id) throws ProjectException;

}
