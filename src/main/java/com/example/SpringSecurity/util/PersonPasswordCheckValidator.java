package com.example.SpringSecurity.util;

import com.example.SpringSecurity.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PersonPasswordCheckValidator {

    public void validate(Person person, String passwordToCheck, Errors errors){
        if (!person.getPassword().equals(passwordToCheck))
            errors.rejectValue("password","", "Пароли не совпадают");
    }
}
