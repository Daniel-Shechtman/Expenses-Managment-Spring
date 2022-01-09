package com.example.helloworld.models;

import com.example.helloworld.classes.Category;
import com.example.helloworld.helpers.DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryModel {
    public Category getCategoryByName(String name)
    {
        Category category = null;
        if (!name.isBlank()) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "SELECT * FROM categories WHERE name = '" + name+"'";
                ResultSet rs = DataBase.selectAll(connection, query);
                if (rs != null) {
                    category = new Category();
                    category.setName(rs.getString("name"));
                    category.setId(rs.getInt("id"));
                    category.setAccountId(rs.getInt("account_id"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return category;
    }
    public String getCategoryNameById(int id) {
        if (id > 0) {
            try (Connection connection = DataBase.getConnection()) {

                String query = "SELECT name FROM categories WHERE id = " + id;
                ResultSet rs = DataBase.selectAll(connection, query);

                if (rs != null) {
                    return rs.getString("name");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return "";
    }
}
