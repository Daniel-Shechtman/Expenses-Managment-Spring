package com.ilandaniel.project.models;

import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IValidator;
import com.ilandaniel.project.validators.ExpenseValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Category logic methods
 */
public class ExpenseModel {

    private final IValidator validator;

    public ExpenseModel() {
        validator = new ExpenseValidator();
    }

    /**
     * add new expense into the db
     *
     * @throws ProjectException
     */
    public void addNewExpense(ExpenseDTO expenseDTO) throws ProjectException {
        String errors = validator.validate(expenseDTO);
        if (errors.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(expenseDTO);
                jsonObject.put("current_user_id", String.valueOf(Helper.loggedInAccount.getId()));
                HttpClient httpClient = HttpClient.newBuilder().build();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/expenses/addExpense"))
                        .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                        .build();
                HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (httpResponse.statusCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    throw new ProjectException(httpResponse.body());
                }


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            throw new ProjectException(errors);
        }
    }

    /**
     * getting all the expenses of the account by account_id.
     */
    public List<Expense> getAllExpenses(int accountId) throws ProjectException {
        List<Expense> expenses = new LinkedList<>();

        try {
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/expenses/getExpenses/" + accountId)).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

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

        } catch (IOException | InterruptedException e) {
            throw new ProjectException(e.getMessage());
        }


        return expenses;
    }

    /**
     * delete expense from db by expense id
     *
     * @throws ProjectException
     */
    public void deleteSelectedExpenseById(int id) throws ProjectException {

        try {
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/expenses/deleteExpense/" + id)).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                throw new ProjectException(httpResponse.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new ProjectException(e.getMessage());
        }

    }
}


