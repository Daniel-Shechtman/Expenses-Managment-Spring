package com.example.helloworld.controllers;

import com.example.helloworld.dtos.AccountLoginDTO;
import com.example.helloworld.dtos.AccountRegisterDTO;
import com.example.helloworld.helpers.DataBase;
import com.example.helloworld.helpers.Security;
import com.example.helloworld.validators.LoginValidator;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/account")
public class AccountController {
    LoginValidator validator = new LoginValidator();

    @RequestMapping(path="/login")
    public ResponseEntity<String> login(HttpServletRequest request)
    {
        StringBuilder errorsBuilder = new StringBuilder();
        BufferedReader reader = null;
        if(request != null){
            try(Connection connection = DataBase.getConnection()){
                reader = request.getReader();
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    buffer.append(System.lineSeparator());
                }
                String data = buffer.toString();
                JSONObject object = new JSONObject(data);
                AccountLoginDTO accountLoginDTO = new AccountLoginDTO(object.getString("username"),object.getString("password"));
                errorsBuilder.append(validator.validate(accountLoginDTO));
                if(errorsBuilder.isEmpty()){
                    ResultSet rs = DataBase.selectAll(connection, "SELECT * FROM accounts WHERE username = '" + accountLoginDTO.getUsername() + "'");
                    if(rs != null){
                        String storedHash = rs.getString("password_hash");
                        String hash = Security.sha512Encryption(accountLoginDTO.getPassword());
                        if (!hash.equals(storedHash)) {
                            errorsBuilder.append("Incorrect Password.. Please try again\n");
                        }
                    }else{
                        errorsBuilder.append("username is wrong\n");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(errorsBuilder.toString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/createAccount")
    public ResponseEntity<String> createAccount(HttpServletRequest request)
    {
        BufferedReader reader = null;
        StringBuilder errorsBuilder = new StringBuilder();
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
                AccountRegisterDTO client = new AccountRegisterDTO(object.getString("username"),object.getString("password"),object.getString("rePassword"));
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
            } catch (IOException e) {
                e.printStackTrace();
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
