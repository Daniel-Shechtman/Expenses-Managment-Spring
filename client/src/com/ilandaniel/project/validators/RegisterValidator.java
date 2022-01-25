package com.ilandaniel.project.validators;

import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.interfaces.IValidator;

/**
 * Register screen validator that checks that all the information is correct
 */
public class RegisterValidator implements IValidator {
    /**
     * The validate function checks:
     * 1)Username or password or repassword is not empty
     * 2)That the password equals the repassword
     * 3)The password is in the correct length
     * 4)The username is in the correct length
     * At the end the function returns the string errors.
     */
    @Override
    public String validate(Object object) {
        StringBuilder errors = new StringBuilder();
        if (object instanceof AccountRegisterDTO) {
            AccountRegisterDTO accountRegisterDTO = (AccountRegisterDTO) object;
            String username = accountRegisterDTO.getUsername();
            String password = accountRegisterDTO.getPassword();
            String rePassword = accountRegisterDTO.getRePassword();
            if (username.isBlank() || password.isBlank() || rePassword.isBlank()) {
                errors.append("One of the fields is empty\n");
            } else if (!password.equals(rePassword)) {
                errors.append("Password and Repassowrd needs to be equal\n");
            }
            if (password.length() < 6) {
                errors.append("Password must be at least 6 chars\n");
            }
            if (username.length() < 5) {
                errors.append("User name must be at least 5 chars\n");
            }

        }
        return errors.toString();
    }
}
