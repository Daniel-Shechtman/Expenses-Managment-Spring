package com.ilandaniel.project.interfaces;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.exceptions.ProjectException;

/**
 * interface that the viewModel implements with all the viewModel methods
 */
public interface IViewModel {

    /**
     * sets the model in the viewModel
     *
     * @param model
     */
    void setModel(IModel model);

    /**
     * sets the view in the viewModel
     *
     * @param view
     */
    void setView(IView view);

    /**
     * open new screen by screen name
     *
     * @param name
     */
    void showScreen(String name);

    /**
     * add new category into the db
     *
     * @param category
     * @return
     * @throws ProjectException
     */
    void addCategory(Category category);

    /**
     * delete category by category name
     *
     * @param categoryName
     * @return
     * @throws ProjectException
     */
    void deleteCategory(String categoryName);

    /**
     * get all categories. return list of categories names
     *
     * @return
     */
    void getAllCategories();

    /**
     * load the Home expenses table with expenses
     *
     * @param id
     */
    void initTableExpenses(int id);


    /**
     * login to the app.
     *
     * @return
     * @paramaccountLoginDTO
     */
    void loginUser(AccountLoginDTO client);


    /**
     * add new expense to db
     *
     * @param expenseDTO
     * @return
     * @paramcategoryName
     **/
    void addNewExpense(ExpenseDTO expenseDTO);

    /**
     * get the category id by category name
     *
     * @param expenseDTO
     */

    void getCategoryIdByName(ExpenseDTO expenseDTO);

    /**
     * create new account in the app
     *
     * @return
     * @throws ProjectException
     * @paramaccountRegisterDTO
     */
    void createAccount(AccountRegisterDTO client);

    /**
     * Getting expense report from specific dates(E.g. from 1-1-2022 to 31-12-2022)
     *
     * @param fromDate
     * @param toDate
     */
    void getReport(String fromDate, String toDate);

    /**
     * Login out the current user that signed in
     */
    void logout();

    /**
     * delete selected row from the expense table (Main screen) by using the id of that specific row.
     *
     * @param id
     */
    void deleteSelected(int id);
}
