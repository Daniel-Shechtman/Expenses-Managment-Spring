package com.ilandaniel.project.models;

import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.interfaces.IValidator;
import com.ilandaniel.project.validators.LoginValidator;
import com.ilandaniel.project.validators.RegisterValidator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
        String errors = new String();

        try {
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/account/login/" + client.getUsername() + "/" + client.getPassword())).GET().build();
            HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            errors = httpResponse.body().toString();


        } catch (IOException e) {
            throw new ProjectException(e.getMessage());
        } catch (InterruptedException e) {
            throw new ProjectException(e.getMessage());
        }

        return errors;
    }

    /**
     * create new account and insert the data in the DB.
     * hashing the password before inserting the data.
     * using RegisterValidator for data validation.
     * see validators/RegisterValidator.
     */
    public String createAccount(AccountRegisterDTO client) throws ProjectException {
        String errors = new String();

        try {
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/account/createAccount/" + client.getUsername() + "/" + client.getPassword())).GET().build();
            HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            errors = httpResponse.body().toString();


        } catch (IOException e) {
            throw new ProjectException(e.getMessage());
        } catch (InterruptedException e) {
            throw  new ProjectException(e.getMessage());
        }


        return errors;
    }
}
