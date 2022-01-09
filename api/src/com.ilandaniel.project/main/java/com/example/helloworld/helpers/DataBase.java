package com.example.helloworld.helpers;

import java.sql.*;

public class DataBase {

    /**
     * My-Sql connection.
     * get connection object
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/expence_managment_db?" +
                    "user=root&password=");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * select all rows from the DB by select query and active connection object.
     * returning ResultSet that contains all data searched.
     */
    public static ResultSet selectAll(Connection connection, String query) {
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);

                if (rs.isBeforeFirst()) {
                    rs.next();
                    return rs;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     * get account id from the DB by username
     */
    public static int getAccountId(String username) {
        try (Connection connection = getConnection()) {
            String query = "SELECT id FROM accounts WHERE username = '" + username + "'";
            ResultSet rs = selectAll(connection, query);
            if (rs != null) {
                return rs.getInt("id");
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return -1;
    }

}
