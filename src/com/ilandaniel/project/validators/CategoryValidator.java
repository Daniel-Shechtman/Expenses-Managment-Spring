package com.ilandaniel.project.validators;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.helpers.DataBase;
import com.ilandaniel.project.interfaces.IValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryValidator implements IValidator {
    @Override
    public String validate(Object object) {
        if (object != null && object instanceof Category) {
            Category category = (Category) object;
            StringBuilder errorsBuilder = new StringBuilder();

            if (category.getName() != null && category.getName().isEmpty()) {
                errorsBuilder.append("name of the category can't be empty\n");
            } else {
                try (Connection connection = DataBase.getConnection()) {
                    String query = "SELECT * FROM categories WHERE name ='" + category.getName() + "'";
                    ResultSet rs = DataBase.selectAll(connection, query);
                    if (rs != null) {
                        errorsBuilder.append("this category all ready exits ");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (category.getIcon().isEmpty()) {
                //we need to add a general image
            }
            return errorsBuilder.toString();

        }
        return null;
    }


}
