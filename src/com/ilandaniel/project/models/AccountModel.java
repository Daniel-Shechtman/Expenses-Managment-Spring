package com.ilandaniel.project.models;

import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.DataBase;
import com.ilandaniel.project.helpers.Security;
import com.ilandaniel.project.interfaces.IValidator;
import com.ilandaniel.project.validators.LoginValidator;
import com.ilandaniel.project.validators.RegisterValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountModel {

    private final IValidator loginValidator;
    private final IValidator registerValidator;

    public AccountModel() {
        loginValidator = new LoginValidator();
        registerValidator = new RegisterValidator();
    }

    /**
     * method to log in into the app.
     * checking if the data is valid in the DB.
     * using RegisterValidator for data validation.
     * see validators/LoginValidator.
     */
    public String loginUser(AccountLoginDTO client) throws ProjectException {
        String errors = loginValidator.validate(client);
        StringBuilder errorsBuilder = new StringBuilder(errors);
        if (errorsBuilder.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                ResultSet rs = DataBase.selectAll(connection, "SELECT * FROM accounts WHERE username = '" + client.getUsername() + "'");

                if (rs != null) {
                    String storedHash = rs.getString("password_hash");
                    String hash = Security.sha512Encryption(client.getPassword());
                    if (!hash.equals(storedHash)) {
                        errorsBuilder.append("Hash is not the same");
                    }
                } else {
                    errorsBuilder.append("username is wrong\n");
                }

            } catch (SQLException e) {
                throw new ProjectException("LoginModel, loginUser method. error: " + e.getMessage());
            }

        }

        return errorsBuilder.toString();
    }

    /**
     * create new account and insert the data in the DB.
     * hashing the password before inserting the data.
     * using RegisterValidator for data validation.
     * see validators/RegisterValidator.
     */
    public String createAccount(AccountRegisterDTO client) throws ProjectException {
        String errors = registerValidator.validate(client);
        StringBuilder errorsBuilder = new StringBuilder(errors);

        if (errorsBuilder.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                ResultSet rs = DataBase.selectAll(connection, "SELECT * FROM accounts WHERE username = '" + client.getUsername() + "'");
                if (rs != null) {
                    errorsBuilder.append("This username all ready exits\n");
                }
                if (errorsBuilder.isEmpty()) {
                    // the mysql insert statement
                    String query = " insert into accounts (username, password_hash)"
                            + " values (?, ?)";

                    String hashStr = Security.sha512Encryption(client.getPassword());

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setString(1, client.getUsername());
                    preparedStmt.setString(2, hashStr);

                    // execute the preparedstatement
                    preparedStmt.execute();
                    connection.close();
                }
            } catch (SQLException e) {
                throw new ProjectException("RegisterModel, createAccount. error: " + e.getMessage());
            }
        }


        return errorsBuilder.toString();
    }
}
