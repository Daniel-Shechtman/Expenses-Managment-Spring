package com.ilandaniel.project.viewmodels;

import com.ilandaniel.project.classes.Account;
import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IModel;
import com.ilandaniel.project.interfaces.IView;
import com.ilandaniel.project.interfaces.IViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * view model class that implements IViewModel and contains all the methods of the models
 */
public class ViewModel implements IViewModel {

    private IModel model;
    private IView view;

    private final ExecutorService executor;


    public ViewModel() {
        executor = Executors.newFixedThreadPool(3);
    }

    /**
     * setting the model from type IModel
     *
     * @param model
     */
    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    /**
     * setting the view from type IView
     *
     * @param view
     */
    @Override
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * switches between the different screens
     *
     * @param name
     */
    @Override
    public void showScreen(String name) {
        if (name != null) {
            view.showScreen(name);
        }
    }

    /**
     * receives category and sends it to the model,if there are no errors message appears on the screen that category added successfully
     * and if any errors accord message appears with the specific errors
     *
     * @param category
     */
    @Override
    public void addCategory(Category category) {
        executor.submit(() -> {
            String errors;
            try {
                errors = model.addCategory(category);
                if (errors.isEmpty()) {
                    view.addCategory(category.getName());
                    view.showMessage("Category manager", "Category added successfully");

                } else {
                    view.showMessage("Errors", errors);
                }
            } catch (ProjectException e) {
                view.showMessage("Error", e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * method that receives category name passes it to the model,the model returns a boolean value
     * if the value is true a message appears on users screen,otherwise message with the errors appears.
     *
     * @param categoryName
     */
    @Override
    public void deleteCategory(String categoryName) {
        executor.submit(() -> {
            try {
                boolean isDeleted = model.deleteCategory(categoryName);
                if (isDeleted) {
                    view.deleteCategory(categoryName);
                    view.showMessage("Category manager", "Category deleted successfully");
                }
            } catch (ProjectException e) {
                e.printStackTrace();
                view.showMessage("Errors", e.getMessage());
            }
        });
    }

    /**
     * getting list of categories from the model and passes the list to the view
     */
    @Override
    public void getAllCategories() {
        executor.submit(() -> {
            try {
                List<String> categoryNames = model.getAllCategories();
                view.loadCategoriesNames(categoryNames);
            } catch (ProjectException e) {
                view.showMessage("Error", e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * init the table of expenses by the current user logged in
     *
     * @param id
     */
    @Override
    public void initTableExpenses(int id) {
        executor.submit(() -> {
            try {
                List<Expense> expenses = model.getAllExpenses(id);
                if (expenses != null) {
                    view.loadTableExpenses(expenses);
                }
            } catch (ProjectException e) {
                view.showMessage("Errors", e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * receives an account login data and passes it to the model,if an error accords a message will appear on users screen otherwise the user logged in.
     *
     * @param client
     */
    @Override
    public void loginUser(AccountLoginDTO client) {
        executor.submit(() -> {
            String errors = null;
            try {
                errors = model.loginUser(client);
            } catch (ProjectException e) {
                view.showMessage("Errors", e.getMessage());
                e.printStackTrace();
            }
            if (errors != null && !errors.isEmpty()) {
                String finalErrors = errors;
                view.showMessage("Errors", finalErrors);

            } else {
                int id = 0;
                try {
                    id = model.getAccountIdByUsername(client.getUsername());
                } catch (ProjectException e) {
                    view.showMessage("Errors", e.getMessage());
                    e.printStackTrace();
                }
                Helper.loggedInAccount = new Account(id, client.getUsername());
                view.showScreen("Home");
            }
        });
    }

    /**
     * receives and expense data and passes it to the model,if no exception accords a message will appear on users screen otherwise message with the errors will show
     *
     * @param expenseDTO
     */
    @Override
    public void addNewExpense(ExpenseDTO expenseDTO) {
        executor.submit(() -> {
            try {
                model.addNewExpense(expenseDTO);
                view.showMessage("Add Expense", "Expense added successfully");
                view.showScreen("Home");

            } catch (ProjectException e) {
                view.showMessage("Errors", e.getMessage());
                e.printStackTrace();

            }
        });
    }

    /**
     * receives and expense data and passes the id of the category to the view
     *
     * @param expenseDTO
     */
    @Override
    public void getCategoryIdByName(ExpenseDTO expenseDTO) {
        executor.submit(() -> {
            int catId;
            try {
                catId = model.getCategoryIdByName(expenseDTO.getCategoryName());
                expenseDTO.setCategoryId(catId);
                addNewExpense(expenseDTO);
            } catch (ProjectException e) {
                view.showMessage("Errors", e.getMessage());
                e.printStackTrace();
            }
        });


    }

    /**
     * Registration to the app.
     * checking with the validator if there are no errors and then checking in the DB if the username is not exists
     * using RegisterModel
     */

    @Override
    public void createAccount(AccountRegisterDTO client) {
        executor.submit(() -> {
            try {
                String errors = model.createAccount(client);
                if (errors != null && !errors.isEmpty()) {
                    view.showMessage("Errors", errors);

                } else {
                    view.showMessage("Register", "Registered Successful");
                    view.showScreen("Login");
                }

            } catch (ProjectException e) {
                view.showMessage("Error", e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Getting expense report from specific dates(E.g. from 1-1-2022 to 31-12-2022)
     *
     * @param fromDate
     * @param toDate
     */
    @Override
    public void getReport(String fromDate, String toDate) {
        executor.submit(() -> {
            try {
                List<Expense> expensesList = model.getReport(fromDate, toDate);
                view.loadReportsExpenses(expensesList);
            } catch (ProjectException e) {
                view.showMessage("Errors", e.getMessage());
                e.printStackTrace();

            }
        });

    }

    /**
     * logging out the current user
     */
    @Override
    public void logout() {
        executor.submit(() -> {
            Helper.loggedInAccount = null;
            showScreen("Login");
        });
    }

    /**
     * delete selected row from the expense table by receiving the id of that specific row
     *
     * @param id
     */
    @Override
    public void deleteSelected(int id) {
        executor.submit(() -> {
            try {
                model.deleteSelected(id);
                initTableExpenses(Helper.loggedInAccount.getId());
            } catch (ProjectException e) {
                view.showMessage("Error", e.getMessage());
                e.printStackTrace();
            }
        });
    }


}
