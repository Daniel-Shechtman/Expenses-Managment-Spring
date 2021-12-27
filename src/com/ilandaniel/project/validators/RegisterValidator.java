package com.ilandaniel.project.validators;

import com.ilandaniel.project.dtos.AccountRegisterDTO;
import com.ilandaniel.project.interfaces.IValidator;

public class RegisterValidator implements IValidator {

    /**
     * validate Registration by checking:
     * 1) there are no empty fields.
     * 2) password is equals to the re-password
     */
    @Override
    public String validate(Object object) {
        if (object != null && object instanceof AccountRegisterDTO) {
            AccountRegisterDTO client = (AccountRegisterDTO) object;
            StringBuilder stringBuilder = new StringBuilder();
            if (client.getUsername().isEmpty() || client.getRePassword().isEmpty() || client.getPassword().isEmpty()) {
                stringBuilder.append("You have to fill all fields to register\n");
            }

            if (client.getPassword().length() < 6) {
                stringBuilder.append("Password must be at least 6 chars\n");
            }

            if (client.getUsername().length() < 4) {
                stringBuilder.append("Username must be at least 4 chars\n");
            }

            if (!client.getPassword().equals(client.getRePassword())) {
                stringBuilder.append("The password don't match the rePassword\n");
            }
            return stringBuilder.toString();
        }
        return null;
    }
}
