package com.ilandaniel.project.validators;

import com.ilandaniel.project.interfaces.IValidator;

public class ReportsValidator implements IValidator {
    @Override
    public String validate(Object object) {
        StringBuilder errors = new StringBuilder();
        if(object instanceof String){
            String date = (String) object;
            String[] test = date.split("-");
            if(test.length !=3){
                errors.append("Please enter valid format for date!");

            }else  {
                try{
                    int day = Integer.parseInt(test[0]);
                    int month = Integer.parseInt(test[1]);
                    int year = Integer.parseInt(test[2]);


                    if(day <1 || day >31){
                        errors.append("Invalid day");
                    }
                    if(month<1 || month>12){
                        errors.append("Invalid month");
                    }
                    if(year<2021 || year>2100){
                        errors.append("Invalid year");
                    }
                }catch (Exception e){
                    errors.append("Wrong format");
                }


            }


        }
        return errors.toString();
    }
}
