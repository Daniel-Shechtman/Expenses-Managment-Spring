package com.ilandaniel.project.tests;


import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.interfaces.IModel;
import com.ilandaniel.project.models.Model;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterModelTest {

    @org.junit.jupiter.api.Test
    void createAccount() {
        AccountRegisterDTO account = new AccountRegisterDTO("Test", "123456", "123456");
        IModel registerModel = new Model();

        try {
            String actual = registerModel.createAccount(account);

            String expected = "This username all ready exits\n";
            assertEquals(expected, actual);

            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, StandardCharsets.UTF_8);


            account.setUsername(generatedString);
            actual = registerModel.createAccount(account);
            assertEquals("", actual);

        } catch (ProjectException e) {
            e.printStackTrace();
        }

    }
}