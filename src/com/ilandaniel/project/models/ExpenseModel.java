package com.ilandaniel.project.models;

import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.DataBase;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IValidator;
import com.ilandaniel.project.validators.ExpenseValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ExpenseModel {

    private final IValidator validator;
    private final CategoryModel categoryModel = new CategoryModel();

    public ExpenseModel() {
        validator = new ExpenseValidator();
    }

    public void addNewExpense(ExpenseDTO expenseDTO) throws ProjectException {
        String errors = validator.validate(expenseDTO);
        if (errors.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "INSERT INTO expenses (account_id,category_id,cost,currency,info,date_created) VALUES(?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Helper.loggedInAccount.getId());
                preparedStatement.setInt(2, expenseDTO.getCategoryId());
                preparedStatement.setFloat(3, Float.parseFloat(expenseDTO.getCost()));
                preparedStatement.setString(4, expenseDTO.getCurrency());
                preparedStatement.setString(5, expenseDTO.getInfo());
                preparedStatement.setLong(6,System.currentTimeMillis());
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
            HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            JSONArray array = new JSONArray(httpResponse.body().toString());
            for(int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                Expense expense = new Expense();
                expense.setId(object.getInt("id"));
                expense.setInfo(object.getString("info"));
                expense.setCost(object.getFloat("cost"));
                expense.setCurrency(object.getString("currency"));
                expense.setCategoryId(object.getInt("categoryId"));
                expense.setDateCreated(new Date(object.getLong("dateCreated")));
                expense.setCategoryName(categoryModel.getCategoryNameById(expense.getCategoryId()));
                expenses.add(expense);
            }

        } catch (IOException e) {
            throw new ProjectException(e.getMessage());
        } catch (InterruptedException e) {
            throw  new ProjectException(e.getMessage());
        }


        return expenses;
    }
    }


