package com.ilandaniel.project.tests;


import com.ilandaniel.project.classes.Account;
import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IModel;
import com.ilandaniel.project.models.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReportModelTest {
    IModel reportModel;

    @BeforeEach
    private void init() {
        reportModel = new Model();
        Helper.loggedInAccount = new Account(7, "Test2");
    }

    @Test
    void getReport() {
        try {
            String dateFrom = "1-1-2022";
            String dateTo = "5-1-2022";
            List<Expense> expenses = new LinkedList<>();
            String finalDateFrom = dateFrom;
            String finalDateTo = dateTo;
            assertThrows(ProjectException.class, () -> reportModel.getReport(finalDateFrom, finalDateTo));
            dateFrom = "1--1-2022";
            String finalDateFrom1 = dateFrom;
            String finalDateTo1 = dateTo;
            assertThrows(ProjectException.class, () -> reportModel.getReport(finalDateFrom1, finalDateTo1));
            dateFrom = "1-1-2022";
            dateTo = "11-1-2022";
            expenses = reportModel.getReport(dateFrom, dateTo);
            assertEquals(2, expenses.size());
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }


}