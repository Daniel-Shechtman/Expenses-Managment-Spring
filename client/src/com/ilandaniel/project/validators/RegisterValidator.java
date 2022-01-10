package com.ilandaniel.project.validators;

import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.interfaces.IValidator;

public class RegisterValidator implements IValidator {
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
