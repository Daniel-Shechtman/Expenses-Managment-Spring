package com.ilandaniel.project.models;

import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.exceptions.ProjectException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AccountModel {

    /**
     * method to log in into the app.
     * checking if the data is valid in the DB.
     * using RegisterValidator for data validation.
     * see validators/LoginValidator.
     */
    public String loginUser(AccountLoginDTO client) throws ProjectException {
        String errors;

        try {
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/account/login/" + client.getUsername() + "/" + client.getPassword())).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            errors = httpResponse.body();


        } catch (IOException | InterruptedException e) {
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
        String errors;

        try {
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/account/createAccount/" + client.getUsername() + "/" + client.getPassword())).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            errors = httpResponse.body();


        } catch (IOException | InterruptedException e) {
            throw new ProjectException(e.getMessage());
        }


        return errors;
    }

    public int getAccountIdByUsername(String username) throws ProjectException {
        int id = -1;
        try {
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/account/getAccountId/" + username)).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                throw new ProjectException(httpResponse.body());
            }
            id = Integer.parseInt(httpResponse.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return id;
    }
}
