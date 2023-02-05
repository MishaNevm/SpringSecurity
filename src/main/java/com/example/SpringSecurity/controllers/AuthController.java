package com.example.SpringSecurity.controllers;

import com.example.SpringSecurity.models.Person;
import com.example.SpringSecurity.sevices.PersonService;
import com.example.SpringSecurity.util.PersonPasswordCheckValidator;
import com.example.SpringSecurity.util.PersonUsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Validated
@RequestMapping("/auth")
public class AuthController {

    private final PersonUsernameValidator personUsernameValidator;
    private final PersonPasswordCheckValidator personPasswordCheckValidator;
    private final PersonService personService;

    @Autowired
    public AuthController(PersonUsernameValidator personUsernameValidator, PersonPasswordCheckValidator personPasswordCheckValidator, PersonService personService) {
        this.personUsernameValidator = personUsernameValidator;
        this.personPasswordCheckValidator = personPasswordCheckValidator;
        this.personService = personService;
    }

    @GetMapping("/login")
    public String auth () {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration (@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @RequestParam("passwordToCheck") String passwordToCheck){
        personUsernameValidator.validate(person, bindingResult);
        personPasswordCheckValidator.validate(person, passwordToCheck, bindingResult);
        if (bindingResult.hasErrors()) return "auth/registration";
        personService.save(person);
        return "redirect:/auth/login";
    }
}
