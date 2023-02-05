package com.example.SpringSecurity.util;

import com.example.SpringSecurity.models.Person;
import com.example.SpringSecurity.sevices.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonUsernameValidator implements Validator {

    private final PersonService personService;

    public PersonUsernameValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person newPerson = (Person) target;
        Person personFromDB = personService.findByName(((Person) target).getName());
        if (personFromDB != null && !personFromDB.equals(newPerson)) {
            errors.rejectValue("name", "", "Имя пользовател уже занято");
        }
    }
}
