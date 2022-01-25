package com.ilandaniel.project.views;

import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * Login existing user screen
 */
public class LoginScreen extends BaseScreen {
    private JLabel labelTitle, labelUsername, labelPassword;
    private JButton btnLogin, btnRegister;
    private JTextField textFieldUsername;
    private JPasswordField textFieldPassword;
    private GridBagConstraints constraints;

    public LoginScreen() {
    }

    @Override
    public void init() {
        //initializing the components
        labelTitle = new JLabel("Log in");
        labelUsername = new JLabel("username: ");
        labelPassword = new JLabel("password: ");
        btnLogin = new JButton("log in");
        btnRegister = new JButton("create account");
        //set the text field size
        textFieldUsername = new JTextField(15);
        textFieldPassword = new JPasswordField(15);
        constraints = new GridBagConstraints();
    }

    @Override
    public void start() {
        //setting the jFrame properties
        this.setLayout(new GridBagLayout());
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //placing the components on the grid
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        this.add(labelTitle, constraints);
        constraints.gridwidth = 1;

        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(labelUsername, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        this.add(textFieldUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        this.add(labelPassword, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        this.add(textFieldPassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        this.add(btnLogin, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        this.add(btnRegister, constraints);
        //setting the location of the screen to be at the center of the screen
        setLocationRelativeTo(null);
        this.pack();

        //adding action listener to the register button
        btnRegister.addActionListener(e -> viewModel.showScreen("Register"));

        //adding action listener to the login button
        btnLogin.addActionListener(e -> {
            String username, password;
            username = textFieldUsername.getText();
            password = String.valueOf(textFieldPassword.getPassword());
            AccountLoginDTO accountLoginDTO = new AccountLoginDTO(username, password);
            viewModel.loginUser(accountLoginDTO);

        });
    }


    @Override
    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;
    }


}



