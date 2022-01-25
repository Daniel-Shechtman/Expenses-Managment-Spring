package com.ilandaniel.project.validators;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IValidator;
import com.ilandaniel.project.models.CategoryModel;

/**
 * Expense screen fields validator that checks if all the information is correct
 */
public class ExpenseValidator implements IValidator {

    /**
     * Validate Expense by checking:
     * 1) It's not null or there are empty fields.
     * 2) Cost is above 0.
     * 3) Valid category id by checking it in the DB.
     * At the end the function returns the string errors.
     */
    @Override
    public String validate(Object object) {

        if (object instanceof ExpenseDTO expense) {
            StringBuilder errorsBuilder = new StringBuilder();

            try {
                if (!Helper.isNumeric(expense.getCost())) {
                    errorsBuilder.append("cost cannot be string,please enter only numbers");
                } else if (Float.parseFloat(expense.getCost()) < 0) {
                    errorsBuilder.append("cost can't be negative\n");
                }

                Category category = CategoryModel.getCategoryById(expense.getCategoryId());
                if (category == null) {
                    errorsBuilder.append("wrong category\n");
                }

                if (expense.getInfo().isEmpty()) {
                    errorsBuilder.append("you need to add some info\n");
                }
                return errorsBuilder.toString();

            } catch (ProjectException e) {
                e.printStackTrace();
            }


        }
        return null;
    }
}
