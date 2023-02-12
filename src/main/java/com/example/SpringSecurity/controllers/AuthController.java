package com.example.SpringSecurity.controllers;

import com.example.SpringSecurity.models.Person;
import com.example.SpringSecurity.services.PersonDetailsService;
import com.example.SpringSecurity.services.PersonService;
import com.example.SpringSecurity.util.PersonUsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final PersonService personService;
    private final PersonUsernameValidator personUsernameValidator;

    @Autowired
    public AuthController( PersonService personService, PersonUsernameValidator personUsernameValidator) {
        this.personService = personService;
        this.personUsernameValidator = personUsernameValidator;
    }


    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration (@ModelAttribute("person")Person person) {
        return "auth/registration";
    }
    @PostMapping("/registration")
    public String registration (@ModelAttribute("person")@Valid Person person, BindingResult bindingResult) {
        personUsernameValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "auth/registration";
        personService.save(person);
        return "redirect:/login";
    }
}
