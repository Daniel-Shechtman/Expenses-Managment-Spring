package com.example.helloworld.controllers;

import com.example.helloworld.classes.Expense;
import com.example.helloworld.helpers.DataBase;
import com.example.helloworld.models.CategoryModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportsController {


    @RequestMapping(path = "/getReport/{fromDateStr}/{toDateStr}/{current_account_id}")
    public ResponseEntity<Object> getReports(@PathVariable String fromDateStr, @PathVariable String toDateStr, @PathVariable int current_account_id){
        CategoryModel categoryModel = new CategoryModel();
        List<Expense> filteredExpenses = new LinkedList<>();
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try (Connection connection = DataBase.getConnection()) {
            Date fromDate = simpleDateFormat.parse(fromDateStr);
            Date toDate = simpleDateFormat.parse(toDateStr);

            long fromDateL = fromDate.getTime();
            long toDateL = toDate.getTime();
            String query = "SELECT * FROM expenses WHERE account_id = " + current_account_id + " AND date_created >= " + fromDateL + " AND date_created <= " + toDateL;
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
                    expense.setCategoryName(categoryModel.getCategoryByName(categoryModel.getCategoryNameById(rs.getInt("category_id"))).getName());
                    filteredExpenses.add(expense);
                }
                while (rs.next());
            } else {
                return new ResponseEntity<>("there are no expenses matches your dates", HttpStatus.BAD_REQUEST);
            }

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(filteredExpenses,HttpStatus.OK);
    }
}
