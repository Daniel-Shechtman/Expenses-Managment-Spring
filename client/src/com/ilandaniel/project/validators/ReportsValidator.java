package com.ilandaniel.project.validators;

import com.ilandaniel.project.interfaces.IValidator;

/**
 * Reports screen validator the checks if all the information is correct
 */
public class ReportsValidator implements IValidator {
    /**
     * Validate function that checks:
     * 1)The object is instanceof String(The from date or to date)
     * 2)The string of the date is separated to three small strings (E.g 22-02-2022 is good input while 222-02-2022 is not a good input)
     * 3)The value of the day is greater than 0 and smaller than 32
     * 4)The value of the month is greater than 0 and smaller than 13
     * 5)The value of the year is greater than 2020 and smaller than 2101
     * At the end the function return string of all the errors
     */
    @Override
    public String validate(Object object) {
        StringBuilder errors = new StringBuilder();
        if (object instanceof String date) {
            String[] test = date.split("-");
            if (test.length != 3) {
                errors.append("Please enter valid format for date!");

            } else {
                try {
                    int day = Integer.parseInt(test[0]);
                    int month = Integer.parseInt(test[1]);
                    int year = Integer.parseInt(test[2]);


                    if (day < 1 || day > 31) {
                        errors.append("Invalid day");
                    }
                    if (month < 1 || month > 12) {
                        errors.append("Invalid month");
                    }
                    if (year < 2021 || year > 2100) {
                        errors.append("Invalid year\nmin year = 2021\nmax year = 2100");
                    }
                } catch (Exception e) {
                    errors.append("Wrong format");
                }


            }


        }
        return errors.toString();
    }
}
