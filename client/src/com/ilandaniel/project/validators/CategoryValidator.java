package com.ilandaniel.project.validators;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.interfaces.IValidator;

/**
 * Category screen fields validator that checks if all the information is correct
 */
public class CategoryValidator implements IValidator {
    /**
     * The validate function checks:
     * 1)The object is instanceof category object
     * 2)The category name is not null or empty
     * At the end the function returns the string errors.
     */
    @Override
    public String validate(Object object) {
        if (object instanceof Category category) {
            StringBuilder errorsBuilder = new StringBuilder();

            if (category.getName() != null && category.getName().isEmpty()) {
                errorsBuilder.append("name of the category can't be empty\n");
            }
            return errorsBuilder.toString();

        }
        return null;
    }


}
