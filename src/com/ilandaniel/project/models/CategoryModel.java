package com.ilandaniel.project.models;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.DataBase;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IValidator;
import com.ilandaniel.project.validators.CategoryValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    private final IValidator validator;

    public CategoryModel() {
        validator = new CategoryValidator();
    }


    /**
     * getting category from DB by the category_id.
     */
    public static Category getCategoryById(int id) throws ProjectException {
        try (Connection connection = DataBase.getConnection()) {

            String query = "SELECT * FROM categories WHERE id = '" + id + "'";
            ResultSet rs = DataBase.selectAll(connection, query);

            if (rs != null) {
                Category category = new Category();
                category.setName(rs.getString("name"));
                category.setIcon(rs.getString("icon_path"));
                category.setId(rs.getInt("id"));

                return category;
            }


        } catch (SQLException throwables) {
            throw new ProjectException("DBmodel: getCategory error. " + throwables.getMessage());
        }

        return null;
    }


    public String getCategoryNameById(int id) throws ProjectException {
        if (id > 0) {
            try (Connection connection = DataBase.getConnection()) {

                String query = "SELECT name FROM categories WHERE id = " + id;
                ResultSet rs = DataBase.selectAll(connection, query);

                if (rs != null) {
                    return rs.getString("name");
                }

            } catch (SQLException throwables) {
                throw new ProjectException(throwables.getMessage());
            }

        }

        return "";
    }

    public int getCategoryIdByName(String categoryName) throws ProjectException {
        if (categoryName != null && !categoryName.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "SELECT id FROM categories WHERE name = '" + categoryName + "'";
                ResultSet rs = DataBase.selectAll(connection, query);
                if (rs != null) {
                    return rs.getInt("id");
                }
            } catch (SQLException throwables) {
                throw new ProjectException(throwables.getMessage());
            }
        }

        return 0;
    }

    public static Category getCategoryByName(String categoryName) throws ProjectException {
        try (Connection connection = DataBase.getConnection()) {

            String query = "SELECT * FROM categories WHERE name = '" + categoryName + "'";
            ResultSet rs = DataBase.selectAll(connection, query);

            if (rs != null) {
                Category category = new Category();
                category.setName(rs.getString("name"));
                category.setIcon(rs.getString("icon_path"));
                category.setId(rs.getInt("id"));
                category.setAccountId(rs.getInt("account_id"));

                return category;
            }


        } catch (SQLException throwables) {
            throw new ProjectException("DBmodel: getCategory error. " + throwables.getMessage());
        }

        return null;
    }

    /**
     * add new category into the DB.
     */
    public String addCategory(Category category) throws ProjectException {
        String errors = validator.validate(category);
        String path = Helper.getIconPathByCategoryName(category.getName());
        if (errors.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "INSERT INTO categories (name,icon_path,account_id) VALUES(?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, category.getName());
                preparedStatement.setString(2, path);
                preparedStatement.setInt(3, Helper.loggedInAccount.getId());
                preparedStatement.execute();

            } catch (SQLException throwables) {
                throw new ProjectException("DBmodel: addCategory error. " + throwables.getMessage());
            }
        }
        return errors;
    }


    /**
     * delete category from the DB
     */
    public boolean deleteCategory(String categoryName) throws ProjectException {
        Category category = getCategoryByName(categoryName);
        if (category != null && category.getAccountId() != 0) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "DELETE FROM categories WHERE name=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, categoryName);
                preparedStatement.execute();

                query = "DELETE FROM expenses WHERE category_id = " + category.getId() + " AND account_id = " + Helper.loggedInAccount.getId();
                Statement statement = connection.createStatement();
                statement.execute(query);

                return true;
            } catch (SQLException throwables) {
                throw new ProjectException("DBmodel: deleteCategory error. " + throwables.getMessage());
            }
        } else if (category.getAccountId() == 0) {
            throw new ProjectException("You cant delete root category");
        }
        return false;
    }

    public List<String> getAllCategories() throws ProjectException {
        //String errors = validator.validate() we need to add
        List<String> list = new ArrayList<>();
        try (Connection connection = DataBase.getConnection()) {
            String query = "SELECT name FROM categories WHERE account_id=" + Helper.loggedInAccount.getId() + " OR account_id=0";
            ResultSet rs = DataBase.selectAll(connection, query);
            if (rs != null) {
                do {
                    list.add(rs.getString("name"));
                } while (rs.next());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

}
