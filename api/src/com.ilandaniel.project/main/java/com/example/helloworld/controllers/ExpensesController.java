package com.example.helloworld.controllers;

import com.example.helloworld.classes.Expense;
import com.example.helloworld.helpers.DataBase;
import com.example.helloworld.models.CategoryModel;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/expenses")

public class ExpensesController {
    CategoryModel categoryModel = new CategoryModel();
    @RequestMapping(path = "/getExpenses/{accountId}")
    public ResponseEntity getExpenses(@PathVariable int accountId) {
        List<Expense> returnList = new ArrayList<>();
        try (Connection connection = DataBase.getConnection()) {
            String query = "SELECT * FROM expenses WHERE account_id = " + accountId + " ORDER BY date_created DESC";
            ResultSet rs = DataBase.selectAll(connection, query);
            if (rs != null) {
                do {
                    Expense expense = new Expense();
                    expense.setId(rs.getInt("id"));
                    expense.setInfo(rs.getString("info"));
                    expense.setCost(rs.getFloat("cost"));
                    expense.setCurrency(rs.getString("currency"));
                    expense.setCategoryId(rs.getInt("category_id"));
                    expense.setDateCreated(rs.getLong("date_created"));
                    expense.setCategoryName(categoryModel.getCategoryNameById(expense.getCategoryId()));
                    returnList.add(expense);

                } while (rs.next());
            }


        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return new ResponseEntity(throwable.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(returnList, HttpStatus.OK);
    }

    @RequestMapping(path = "/addExpense")
    @ResponseBody
    public ResponseEntity<String> saveData(HttpServletRequest request, HttpServletResponse response, Model model){
        BufferedReader reader = null;
        if (request != null) {
            try (Connection connection = DataBase.getConnection()) {
                reader = request.getReader();
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    buffer.append(System.lineSeparator());
                }
                String data = buffer.toString();
                JSONObject object = new JSONObject(data);
                String query = "INSERT INTO expenses (account_id,category_id,cost,currency,info,date_created) VALUES(?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,object.getInt("current_user_id") );
                preparedStatement.setInt(2, object.getInt("categoryId"));
                preparedStatement.setFloat(3, object.getFloat("cost"));
                preparedStatement.setString(4, object.getString("currency"));
                preparedStatement.setString(5, object.getString("info"));
                preparedStatement.setLong(6,System.currentTimeMillis());
                preparedStatement.execute();
                preparedStatement.close();

                if(reader != null) {
                    reader.close();
                }

                return new ResponseEntity<>("OK",HttpStatus.OK);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>("Request Is Null",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(path = "/deleteExpense/{expenseId}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable int expenseId){
        try (Connection connection = DataBase.getConnection()) {
            String query = "DELETE FROM expenses WHERE id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, expenseId);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            return new ResponseEntity<>(throwables.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("",HttpStatus.OK);
    }


}
