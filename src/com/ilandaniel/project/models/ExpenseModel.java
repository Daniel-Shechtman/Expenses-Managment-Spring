package com.ilandaniel.project.models;

import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.DataBase;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IValidator;
import com.ilandaniel.project.validators.ExpenseValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                preparedStatement.setLong(6, System.currentTimeMillis());
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
        List<Expense> returnList = new ArrayList<>(5);
        try (Connection connection = DataBase.getConnection()) {

            String query = "SELECT * FROM expenses WHERE account_id = " + accountId;
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


        } catch (SQLException throwables) {
            throw new ProjectException("HomeModel, getAllExpenses method. error: " + throwables.getMessage());
        }

        return returnList;
    }


}
