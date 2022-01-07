package com.ilandaniel.project.validators;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.interfaces.IValidator;

public class CategoryValidator implements IValidator {
    @Override
    public String validate(Object object) {
        if (object instanceof Category) {
            Category category = (Category) object;
            StringBuilder errorsBuilder = new StringBuilder();

            if (category.getName() != null && category.getName().isEmpty()) {
                errorsBuilder.append("name of the category can't be empty\n");
            }
            return errorsBuilder.toString();

        }
        return null;
    }


}
