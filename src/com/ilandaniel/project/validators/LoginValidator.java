package com.ilandaniel.project.validators;

import com.ilandaniel.project.dtos.AccountLoginDTO;
import com.ilandaniel.project.interfaces.IValidator;

public class LoginValidator implements IValidator {


    /**
     * validate Login by checking:
     * 1) there are no empty fields.
     */
    @Override
    public String validate(Object object) {
        if (object instanceof AccountLoginDTO) {
            AccountLoginDTO client = (AccountLoginDTO) object;
            StringBuilder stringBuilder = new StringBuilder();
            if (client.getUsername().isEmpty() || client.getPassword().isEmpty()) {
                stringBuilder.append("One of the fields is empty\n");
            }
            return stringBuilder.toString();
        }
        return null;
    }
}

