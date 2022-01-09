package com.ilandaniel.project.models;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IModel;
import com.ilandaniel.project.validators.ReportsValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Model implements IModel {
    AccountModel accountModel = new AccountModel();
    CategoryModel categoryModel = new CategoryModel();
    ExpenseModel expenseModel = new ExpenseModel();

    @Override
    public String addCategory(Category category) throws ProjectException {
        String errors;
        errors = categoryModel.addCategory(category);

        return errors;
    }

    @Override
    public boolean deleteCategory(String categoryName) throws ProjectException {
        boolean isDeleted;
        isDeleted = categoryModel.deleteCategory(categoryName);

        return isDeleted;
    }

    @Override
    public List<String> getAllCategories() throws ProjectException {
        List<String> categoriesNames;
        categoriesNames = categoryModel.getAllCategories();

        return categoriesNames;
    }

    @Override
    public List<Expense> getAllExpenses(int id) throws ProjectException {
        List<Expense> list;
        list = expenseModel.getAllExpenses(Helper.loggedInAccount.getId());

        return list;
    }

    @Override
    public String loginUser(AccountLoginDTO accountLoginDTO) throws ProjectException {
        String errors;

        errors = accountModel.loginUser(accountLoginDTO);

        return errors;
    }

    @Override
    public void addNewExpense(ExpenseDTO expenseDTO) throws ProjectException {
        expenseModel.addNewExpense(expenseDTO);
    }

    @Override
    public int getCategoryIdByName(String categoryName) throws ProjectException {
        int catId;

        catId = categoryModel.getCategoryIdByName(categoryName);

        return catId;
    }

    @Override
    public String createAccount(AccountRegisterDTO accountRegisterDTO) throws ProjectException {
        String errors;

        errors = accountModel.createAccount(accountRegisterDTO);

        return errors;
    }

    @Override
    public List<Expense> getReport(String fromDateStr, String toDateStr) throws ProjectException {
        List<Expense> expenses = new ArrayList<>();
        ReportsValidator validator = new ReportsValidator();
        String errorFrom = validator.validate(fromDateStr);
        String errorTo = validator.validate(toDateStr);
        if (!errorFrom.isEmpty()) {
            throw new ProjectException("From date field errors:\n" + errorFrom);
        } else if (!errorTo.isEmpty()) {
            throw new ProjectException("To date field errors:\n" + errorTo);
        } else {
            try {
                HttpClient httpClient = HttpClient.newBuilder().build();
                HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/reports/getReport/" + fromDateStr + "/" +
                                toDateStr + "/" +
                                Helper.loggedInAccount.getId()))
                        .GET().build();
                HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                if (httpResponse.statusCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    throw new ProjectException(httpResponse.body());
                } else {
                    JSONArray array = new JSONArray(httpResponse.body());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Expense expense = new Expense();
                        expense.setId(object.getInt("id"));
                        expense.setInfo(object.getString("info"));
                        expense.setCost(object.getFloat("cost"));
                        expense.setCurrency(object.getString("currency"));
                        expense.setCategoryId(object.getInt("categoryId"));
                        expense.setDateCreated(new Date(object.getLong("dateCreated")));
                        expense.setCategoryName(object.getString("categoryName"));
                        expenses.add(expense);
                    }
                }
            } catch (IOException | InterruptedException e) {
                throw new ProjectException(e.getMessage());
            }
        }

        return expenses;
    }

    @Override
    public void deleteSelected(int id) throws ProjectException {
        expenseModel.deleteSelectedExpenseById(id);
    }

    @Override
    public int getAccountIdByUsername(String userName) throws ProjectException {
        return accountModel.getAccountIdByUsername(userName);
    }
}
