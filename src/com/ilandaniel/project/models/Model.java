package com.ilandaniel.project.models;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.DataBase;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IModel;
import com.ilandaniel.project.validators.ReportsValidator;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Model implements IModel {
    AccountModel accountModel = new AccountModel();
    CategoryModel categoryModel = new CategoryModel();
    ExpenseModel expenseModel = new ExpenseModel();

    @Override
    public String addCategory(Category category) throws ProjectException {
        String errors = "";
        errors = categoryModel.addCategory(category);

        return errors;
    }

    @Override
    public boolean deleteCategory(String categoryName) throws ProjectException {
        boolean isDeleted = false;
        isDeleted = categoryModel.deleteCategory(categoryName);

        return isDeleted;
    }

    @Override
    public List<String> getAllCategories() throws ProjectException {
        List<String> categoriesNames = new ArrayList<>();
        categoriesNames = categoryModel.getAllCategories();

        return categoriesNames;
    }

    @Override
    public List<Expense> getAllExpenses(int id) throws ProjectException {
        List<Expense> list = new ArrayList<>();
        list = expenseModel.getAllExpenses(Helper.loggedInAccount.getId());

        return list;
    }

    @Override
    public String getCategoryNameById(int id) throws ProjectException {
        String catName = "";
        catName = categoryModel.getCategoryNameById(id);

        return catName;
    }

    @Override
    public String loginUser(AccountLoginDTO accountLoginDTO) throws ProjectException {
        String errors = "";

        errors = accountModel.loginUser(accountLoginDTO);

        return errors;
    }

    @Override
    public void addNewExpense(ExpenseDTO expenseDTO) throws ProjectException {
        expenseModel.addNewExpense(expenseDTO);
    }

    @Override
    public int getCategoryIdByName(String categoryName) throws ProjectException {
        int catId = -1;

        catId = categoryModel.getCategoryIdByName(categoryName);

        return catId;
    }

    @Override
    public String createAccount(AccountRegisterDTO accountRegisterDTO) throws ProjectException {
        String errors = "";

        errors = accountModel.createAccount(accountRegisterDTO);

        return errors;
    }

    @Override
    public List<Expense> getReport(String fromDateStr, String toDateStr) throws ProjectException {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        List<Expense> filterredExpenses = new ArrayList<>();
        ReportsValidator validator = new ReportsValidator();
        String errorFrom = validator.validate(fromDateStr);
        String errorTo = validator.validate(toDateStr);
        if(!errorFrom.isEmpty()){
            throw new ProjectException("From date field errors:\n" + errorFrom);
        }
        else if(!errorTo.isEmpty()){
            throw new ProjectException("To date field errors:\n" + errorTo);
        }
        else {

            try (Connection connection = DataBase.getConnection())
            {
                Date fromDate = simpleDateFormat.parse(fromDateStr);
                Date toDate = simpleDateFormat.parse(toDateStr);

                long fromDateL = fromDate.getTime();
                long toDateL = toDate.getTime();
                String query = "SELECT * FROM expenses WHERE account_id = " + Helper.loggedInAccount.getId() + " AND date_created >= " + fromDateL + " AND date_created <= " + toDateL;
                ResultSet rs = DataBase.selectAll(connection, query);

                if(rs != null)
                {
                    do
                    {
                        Expense expense = new Expense();
                        expense.setId(rs.getInt("id"));
                        expense.setInfo(rs.getString("info"));
                        expense.setCost(rs.getFloat("cost"));
                        expense.setCurrency(rs.getString("currency"));
                        expense.setCategoryId(rs.getInt("category_id"));
                        expense.setDateCreated(new Date(rs.getLong("date_created")));
                        expense.setCategoryName(categoryModel.getCategoryNameById(expense.getCategoryId()));
                        filterredExpenses.add(expense);
                    }
                    while (rs.next());
                }
                else {
                    throw new ProjectException("there are no expenses matches your dates");
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return filterredExpenses;
    }

    @Override
    public void deleteSelected(int id) throws ProjectException {
        try (Connection connection = DataBase.getConnection()){
            String query = "DELETE FROM expenses WHERE id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
