package com.ilandaniel.project.tests;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.interfaces.IModel;
import com.ilandaniel.project.models.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryModelTest {
    IModel model = new Model();


    @org.junit.jupiter.api.Test
    void addCategory() {
        Category category = new Category("play time");
        try {
            String actual = model.addCategory(category);
            String expected = "";
            assertEquals(expected, actual);

            category.setName("");
            actual = model.addCategory(category);
            assertEquals("name of the category can't be empty\n", actual);


        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void deleteCategory() {
        try {
            boolean yap = model.deleteCategory("Test");
            assertEquals(true, yap);

            yap = model.deleteCategory("Test 2");
            assertEquals(false, yap);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }
}