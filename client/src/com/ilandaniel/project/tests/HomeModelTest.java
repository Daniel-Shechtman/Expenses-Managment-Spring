package com.ilandaniel.project.tests;

import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.interfaces.IModel;
import com.ilandaniel.project.models.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HomeModelTest {
    IModel model;
    int id;

    @BeforeEach
    private void init() {
        model = new Model();
        id = 2;
    }

    @Test
    void getAllExpenses() {
        List<Expense> expenses = new LinkedList<>();
        try {
            expenses = model.getAllExpenses(id);
            assertEquals(2, expenses.size());

        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }


}