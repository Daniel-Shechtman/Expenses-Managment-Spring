package com.ilandaniel.project.interfaces;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.exceptions.ProjectException;

import java.util.List;

/**
 * @author Mosh
 * @see
 */
public interface IModel {
    /**
     * add new category into the db
     *
     * @param category
     * @return String
     * @throws ProjectException
     */
    String addCategory(Category category) throws ProjectException;

    /**
     * delete category by category name
     *
     * @param categoryName
     * @return
     * @throws ProjectException
     */
    boolean deleteCategory(String categoryName) throws ProjectException;

    /**
     * get all categories. return list of categories names
     *
     * @return
     * @throws ProjectException
     */
    List<String> getAllCategories() throws ProjectException;

    /**
     * @param id
     * @return
     * @throws ProjectException
     */
    List<Expense> getAllExpenses(int id) throws ProjectException;


    /**
     * login to the app.
     *
     * @return
     * @throws ProjectException
     * @paramaccountLoginDTO
     */
    String loginUser(AccountLoginDTO client) throws ProjectException;

    /**
     * add new expense to db
     *
     * @param expenseDTO
     * @throws ProjectException
     */
    void addNewExpense(ExpenseDTO expenseDTO) throws ProjectException;

    /**
     * get category id by category name
     *
     * @param categoryName
     * @return
     * @throws ProjectException
     */
    int getCategoryIdByName(String categoryName) throws ProjectException;


    /**
     * create new account in the app
     *
     * @return
     * @throws ProjectException
     * @paramaccountRegisterDTO
     */
    String createAccount(AccountRegisterDTO client) throws ProjectException;

    /**
     * get expenese report for specific account, by from date and to date.
     * date format: dd/MM/yyyy
     *
     * @return
     * @throws ProjectException
     * @paramfromDateStr
     * @paramtoDateStr
     */
    List<Expense> getReport(String fromDate, String toDate) throws ProjectException;

    /**
     * delete expense by expense id
     *
     * @param id
     * @throws ProjectException
     */
    void deleteSelected(int id) throws ProjectException;

    /**
     * get account id by account username
     *
     * @param userName
     * @return
     * @throws ProjectException
     */
    int getAccountIdByUsername(String userName) throws ProjectException;

}
