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

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel {

    private IModel model;
    private IView view;

    private final ExecutorService executor;


    public ViewModel() {
        executor = Executors.newFixedThreadPool(3);
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void showScreen(String name) {
        if (name != null) {
            view.showScreen(name);
        }
    }

    @Override
    public void addCategory(Category category) {
        executor.submit(() -> {
            String errors;
            try {
                errors = model.addCategory(category);
                if (errors.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        view.addCategory(category.getName());
                        view.showMessage("Category manager", "Category added successfully");

                    });

                } else {
                    SwingUtilities.invokeLater(() -> view.showMessage("Errors", errors));
                }
            } catch (ProjectException e) {
                SwingUtilities.invokeLater(() -> view.showMessage("Error", e.getMessage()));
                e.printStackTrace();
            }
        });
    }

    @Override
    public void deleteCategory(String categoryName) {
        executor.submit(() -> {
            try {
                boolean isDeleted = model.deleteCategory(categoryName);
                if (isDeleted) {
                    SwingUtilities.invokeLater(() -> {
                        view.deleteCategory(categoryName);
                        view.showMessage("Category manager", "Category deleted successfully");
                    });
                }
            } catch (ProjectException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> view.showMessage("Errors", e.getMessage()));
            }
        });
    }

    @Override
    public void getAllCategories() {
        executor.submit(() -> {
            try {
                List<String> categoryNames = model.getAllCategories();
                SwingUtilities.invokeLater(() -> view.loadCategoriesNames(categoryNames));
            } catch (ProjectException e) {
                SwingUtilities.invokeLater(() -> view.showMessage("Error", e.getMessage()));
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initTableExpenses(int id) {
        executor.submit(() -> {
            try {
                List<Expense> expenses = model.getAllExpenses(id);
                if (expenses != null) {
                    SwingUtilities.invokeLater(() -> view.loadTableExpenses(expenses));
                }
            } catch (ProjectException e) {
                SwingUtilities.invokeLater(() -> view.showMessage("Errors", e.getMessage()));
                e.printStackTrace();
            }
        });
    }


    @Override
    public void loginUser(AccountLoginDTO client) {
        executor.submit(() -> {
            String errors = null;
            try {
                errors = model.loginUser(client);
            } catch (ProjectException e) {
                SwingUtilities.invokeLater(() -> view.showMessage("Errors", e.getMessage()));
                e.printStackTrace();
            }
            if (errors != null && !errors.isEmpty()) {
                String finalErrors = errors;
                SwingUtilities.invokeLater(() -> view.showMessage("Errors", finalErrors));

            } else {
                int id = 0;
                try {
                    id = model.getAccountIdByUsername(client.getUsername());
                } catch (ProjectException e) {
                    SwingUtilities.invokeLater(() -> view.showMessage("Errors", e.getMessage()));
                    e.printStackTrace();
                }
                Helper.loggedInAccount = new Account(id, client.getUsername());

                SwingUtilities.invokeLater(() -> view.showScreen("Home"));
            }
        });
    }

    @Override
    public void addNewExpense(ExpenseDTO expenseDTO) {
        executor.submit(() -> {
            try {
                model.addNewExpense(expenseDTO);
                SwingUtilities.invokeLater(() -> {
                    view.showMessage("Add Expense", "Expense added successfully");
                    view.showScreen("Home");
                });

            } catch (ProjectException e) {
                SwingUtilities.invokeLater(() -> view.showMessage("Errors", e.getMessage()));
                e.printStackTrace();

            }
        });
    }

    @Override
    public void getCategoryIdByName(ExpenseDTO expenseDTO) {
        executor.submit(() -> {
            int catId;
            try {
                catId = model.getCategoryIdByName(expenseDTO.getCategoryName());
                expenseDTO.setCategoryId(catId);
                addNewExpense(expenseDTO);
            } catch (ProjectException e) {
                SwingUtilities.invokeLater(() -> view.showMessage("Errors", e.getMessage()));
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
                    SwingUtilities.invokeLater(() -> view.showMessage("Errors", errors));

                } else {
                    SwingUtilities.invokeLater(() -> {
                        view.showMessage("Register", "Registered Successful");
                        view.showScreen("Login");
                    });

                }

            } catch (ProjectException e) {
                SwingUtilities.invokeLater(() -> view.showMessage("Error", e.getMessage()));
                e.printStackTrace();
            }
        });
    }

    @Override
    public void getReport(String fromDate, String toDate) {
        executor.submit(() -> {
            try {
                List<Expense> expensesList = model.getReport(fromDate, toDate);
                view.loadReportsExpenses(expensesList);
            } catch (ProjectException e) {
                SwingUtilities.invokeLater(() -> view.showMessage("Errors", e.getMessage()));
                e.printStackTrace();

            }
        });

    }

    @Override
    public void logout() {
        executor.submit(() -> {
            Helper.loggedInAccount = null;
            SwingUtilities.invokeLater(() -> showScreen("Login"));
        });
    }

    @Override
    public void deleteSelected(int id) {
        executor.submit(() -> {
            try {
                model.deleteSelected(id);
                initTableExpenses(Helper.loggedInAccount.getId());
            } catch (ProjectException e) {
                SwingUtilities.invokeLater(() -> view.showMessage("Error", e.getMessage()));
                e.printStackTrace();
            }
        });
    }


}
