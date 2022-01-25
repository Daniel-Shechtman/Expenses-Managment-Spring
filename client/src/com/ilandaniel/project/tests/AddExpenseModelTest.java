package com.ilandaniel.project.tests;

import com.ilandaniel.project.classes.Account;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IModel;
import com.ilandaniel.project.models.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddExpenseModelTest {
    IModel model;

    @BeforeEach
    private void init() {
        model = new Model();
        Helper.loggedInAccount = new Account(2, "Test");
    }


    @Test
    void getAllCategories() {
        List<String> categories = new LinkedList<>();
        try {
            categories = model.getAllCategories();
            assertEquals(5, categories.size());
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getCategoryIdByName() {
        try {
            int id = model.getCategoryIdByName("Food");
            assertEquals(1, id);
            id = model.getCategoryIdByName("NoSuchCategory");
            assertEquals(0, id);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }
}