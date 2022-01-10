package com.example.helloworld.controllers;

import com.example.helloworld.classes.Category;
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
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    CategoryModel categoryModel = new CategoryModel();

    @RequestMapping(path = "/getCategoryIdByName/{name}")
    public ResponseEntity<String> getCategoryNameById(@PathVariable String name) {
        if (!name.isBlank()) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "SELECT id FROM categories WHERE name = '" + name + "'";
                ResultSet rs = DataBase.selectAll(connection, query);
                if (rs != null) {
                    return new ResponseEntity<>(String.valueOf(rs.getInt("id")), HttpStatus.OK);
                }
            } catch (SQLException throwables) {
                throwables.getMessage();
            }
        }

        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(path = "/getCategoryById/{id}")
    public ResponseEntity getCategoryById(@PathVariable int id) {
        Category category = new Category();
        if (id > 0) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "SELECT * FROM categories WHERE id = " + id;
                ResultSet rs = DataBase.selectAll(connection, query);
                if (rs != null) {
                    category.setName(rs.getString("name"));
                    category.setId(rs.getInt("id"));
                }
                return new ResponseEntity(category, HttpStatus.OK);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(path = "/getCategoryByName/{name}")
    public ResponseEntity getCategoryByName(@PathVariable String name) {
        Category category = categoryModel.getCategoryByName(name);
        if(category != null)
        {
            return new ResponseEntity(category, HttpStatus.OK);

        }
        return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping("/addCategory")
    @ResponseBody
    public ResponseEntity addCategory(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
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

                String query = "SELECT * FROM categories WHERE name ='" + object.getString("name") + "' AND account_id="+object.getInt("accountId");
                ResultSet rs = DataBase.selectAll(connection, query);
                if (rs != null) {
                    stringBuilder.append("this category all ready exits ");
                }
                else{
                    query = "INSERT INTO categories (name,account_id) VALUES(?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, object.getString("name"));
                    preparedStatement.setInt(2, object.getInt("accountId"));
                    preparedStatement.execute();

                    return new ResponseEntity("", HttpStatus.OK);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return new ResponseEntity(stringBuilder.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(path = "/deleteCategory/{categoryName}/{current_account_id}")
    public ResponseEntity deleteCategory(@PathVariable String categoryName,@PathVariable int current_account_id){
        Category category = categoryModel.getCategoryByName(categoryName);
        if (category != null && category.getAccountId() != 0) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "DELETE FROM categories WHERE name=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, categoryName);
                preparedStatement.execute();

                query = "DELETE FROM expenses WHERE category_id = " + category.getId() + " AND account_id = " + current_account_id;
                Statement statement = connection.createStatement();
                statement.execute(query);

                return new ResponseEntity("",HttpStatus.OK);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (category.getAccountId() == 0) {
            return new ResponseEntity("You cant delete root category",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(path = "/getAllCategories/{current_account_id}")
    public ResponseEntity getAllCategories(@PathVariable int current_account_id){
        List<String> names = new LinkedList<>();
        try (Connection connection = DataBase.getConnection()) {
            String query = "SELECT name FROM categories WHERE account_id=" + current_account_id + " OR account_id=0";
            ResultSet rs = DataBase.selectAll(connection, query);
            if (rs != null) {
                do {
                    names.add(rs.getString("name"));
                } while (rs.next());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ResponseEntity(names,HttpStatus.OK);
    }
}