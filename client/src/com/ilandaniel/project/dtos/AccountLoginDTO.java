package com.ilandaniel.project.dtos;

/**
 * Account login class that holds information from the login method (Login screen) of the user
 */
public class AccountLoginDTO {
    private String username;
    private String password;

    public AccountLoginDTO(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
