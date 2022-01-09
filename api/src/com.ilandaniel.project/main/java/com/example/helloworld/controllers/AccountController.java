package com.example.helloworld.controllers;

import com.example.helloworld.dtos.AccountLoginDTO;
import com.example.helloworld.helpers.DataBase;
import com.example.helloworld.helpers.Security;
import com.example.helloworld.validators.LoginValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/account")
public class AccountController {
    LoginValidator validator = new LoginValidator();

    @RequestMapping(path="/login/{username}/{password}")
    public ResponseEntity<String> login(@PathVariable String username, @PathVariable String password)
    {
        AccountLoginDTO client = new AccountLoginDTO(username,password);
        String errors = validator.validate(client);
        StringBuilder errorsBuilder = new StringBuilder(errors);
        if (errorsBuilder.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                ResultSet rs = DataBase.selectAll(connection, "SELECT * FROM accounts WHERE username = '" + client.getUsername() + "'");

                if (rs != null) {
                    String storedHash = rs.getString("password_hash");
                    String hash = Security.sha512Encryption(client.getPassword());
                    if (!hash.equals(storedHash)) {
                        errorsBuilder.append("Incorrect Password.. Please try again");
                    }
                } else {
                    errorsBuilder.append("username is wrong\n");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        return new ResponseEntity<>(errorsBuilder.toString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/createAccount/{username}/{password}")
    public ResponseEntity<String> createAccount(@PathVariable String username, @PathVariable String password)
    {
        AccountLoginDTO client = new AccountLoginDTO(username,password);
        String errors = validator.validate(client);
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

                    // create the mysql insert prepared statement
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setString(1, client.getUsername());
                    preparedStmt.setString(2, hashStr);

                    // execute the prepared statement
                    preparedStmt.execute();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(errorsBuilder.toString(), HttpStatus.OK) ;
    }

    @RequestMapping(path = "/getAccountId/{username}")
    public ResponseEntity<String> getAccountId(@PathVariable String username){
        int id = DataBase.getAccountId(username);
        if(id != -1){
            return new ResponseEntity<>(String.valueOf(id), HttpStatus.OK);
        }
        return new ResponseEntity<>("Non Such User",HttpStatus.BAD_REQUEST);
    }

}
