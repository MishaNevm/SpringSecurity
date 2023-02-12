package com.example.SpringSecurity.util;

import com.example.SpringSecurity.models.Person;
import com.example.SpringSecurity.security.PersonDetails;
import com.example.SpringSecurity.services.PersonDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonUsernameValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    public PersonUsernameValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person personToSave = (Person) target;
        try {
            personDetailsService.loadUserByUsername(personToSave.getName());
            errors.rejectValue("name","","Имя пользователя уже занято");
        } catch (Exception ignored) {

        }
    }
}
